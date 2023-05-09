package com.splitwise.expenseservice.services.Impl;

import com.splitwise.expenseservice.entities.Expense;
import com.splitwise.expenseservice.entities.Owe;
import com.splitwise.expenseservice.entities.Paid;
import com.splitwise.expenseservice.entities.Transaction;
import com.splitwise.expenseservice.exceptions.ResourceNotFound;
import com.splitwise.expenseservice.payload.*;
import com.splitwise.expenseservice.respository.ExpenseRepository;
import com.splitwise.expenseservice.respository.OweRepository;
import com.splitwise.expenseservice.respository.PaidRepository;
import com.splitwise.expenseservice.respository.TransactionRepository;
import com.splitwise.expenseservice.services.ExpenseService;
import com.splitwise.expenseservice.services.externalServices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    DecimalFormat decimalFormat = new DecimalFormat("#.##");


    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    PaidRepository paidRepository;

    @Autowired
    OweRepository oweRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserService userService;
    @Override
    public APIResponse addExpense(ExpenseBody expenseBody) {
        try {
            Date date = new Date();
            System.out.println(date);
            Expense expense = new Expense();
            expense.setGroupID(expenseBody.getGroupID());
            expense.setAmount(expenseBody.getAmount());
            expense.setAddedBy(expenseBody.getAddedBy());
            expense.setExpenseName(expenseBody.getExpenseName());
            expense.setDate(date);
            Expense savedExpense = this.expenseRepository.save(expense);

            Set<UserAmount> paidBySet = expenseBody.getPaidBySet();
            for(UserAmount user: paidBySet) {
                Paid paid = new Paid();
                paid.setAmount(user.getAmount());
                paid.setUserID(user.getUserID());
                paid.setExpense(savedExpense);
                this.paidRepository.save(paid);
            }

            Set<UserAmount> owedBySet = expenseBody.getOwedBySet();
            for(UserAmount user: owedBySet) {
                Owe owe = new Owe();
                owe.setAmount(user.getAmount());
                owe.setUserID(user.getUserID());
                owe.setExpense(savedExpense);
                this.oweRepository.save(owe);
            }
            return new APIResponse(savedExpense.getExpenseID(), true);
        } catch (Exception e) {
            return new APIResponse("Error in adding expense", false);
        }
    }

    @Override
    public Set<ExpenseDetail> getExpenseListWithGroupID(String groupID) {
        Set<Expense> expenseSet = this.expenseRepository.findExpenseByGroupID(groupID);
        Set<ExpenseDetail> expenseDetailSet = new HashSet<>();
        for(Expense expense: expenseSet) {
            ExpenseDetail expenseDetail = new ExpenseDetail();
            expenseDetail.setExpenseID(expense.getExpenseID());
            expenseDetail.setExpenseName(expense.getExpenseName());
            expenseDetail.setDate(expense.getDate());
            expenseDetail.setAmount(expense.getAmount());
            expenseDetail.setAddedBy(expense.getAddedBy());
            expenseDetail.setGroupID(expense.getGroupID());
            expenseDetail.setPaidSet(expense.getPaidSet());
            expenseDetail.setOweSet(expense.getOweSet());
            expenseDetailSet.add(expenseDetail);
        }
        return expenseDetailSet;
    }

    @Override
    public APIResponse deleteExpense(String expenseID) {
        try {
            Expense expense = this.expenseRepository.findById(expenseID).orElseThrow(()-> new ResourceNotFound("Expense", "ID"));
            this.expenseRepository.deleteById(expense.getExpenseID());
            return new APIResponse("Successfully deleted expense!", true);
        } catch (Exception e) {
            return new APIResponse("Error deleting expense!", false);
        }
    }

    @Override
    public APIResponse deleteExpenseWithGroupID(String groupID) {
        try {
            Set<Expense> expenses = this.expenseRepository.findExpenseByGroupID(groupID);
            for(Expense e: expenses) {
                this.expenseRepository.deleteById(e.getExpenseID());
            }
            return new APIResponse("Expenses deleted!", true);
        } catch(Exception e) {
            return new APIResponse("Error deleting expenses!", false);
        }
    }

    @Override
    public APIResponse getExpenseDetailByExpenseID(String expenseID) {

        Expense expense = this.expenseRepository.findById(expenseID).orElseThrow(()-> new ResourceNotFound("Expense", "ID"));
        ExpenseDetail expenseDetail = new ExpenseDetail();
        expenseDetail.setExpenseID(expense.getExpenseID());
        expenseDetail.setDate(expense.getDate());
        expenseDetail.setAmount(expense.getAmount());
        expenseDetail.setPaidSet(expense.getPaidSet());
        expenseDetail.setOweSet(expense.getOweSet());
        expenseDetail.setGroupID(expense.getGroupID());
        expenseDetail.setAddedBy(expense.getAddedBy());
        return new APIResponse(expenseDetail,true);
    }

    // function to sort hashmap by values
    public static HashMap<String, Double> sortByValue(HashMap<String, Double> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Double> > list =
                new LinkedList<>(hm.entrySet());

        // Sort the list
        list.sort(Map.Entry.comparingByValue());

        // put data from sorted list to hashmap
        HashMap<String, Double> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    @Override
    public APIResponse settleUpGroup(String groupID) {
        APIResponse apiResponse = new APIResponse();

        try {
            // flush the transactions if found with given groupID;
            this.transactionRepository.deleteTransactionByGroupID(groupID);

            // get expense list from expense_table
            Set<Expense> expenseSet = this.expenseRepository.findExpenseByGroupID(groupID);

            // Settle Up Algorithm

            // balance list will be used to create giver and receiver group of user's
            HashMap<String, Double> balanceList = new HashMap<>();

            for (Expense expense : expenseSet) {
                System.out.println(expense.getExpenseName());

                Set<Paid> paidSet = expense.getPaidSet();
                Set<Owe> oweSet = expense.getOweSet();

                // Create a Hashmap of userID -> Amount for both paid and owe list
                HashMap<String, Double> paidHashMap = new HashMap<>();
                HashMap<String, Double> oweHashMap = new HashMap<>();

                for (Paid paid : paidSet) {
                    paidHashMap.put(paid.getUserID(), paid.getAmount());
                }
                for (Owe owe : oweSet) {
                    oweHashMap.put(owe.getUserID(), owe.getAmount());
                }

                for (Map.Entry<String, Double> paidEntry : paidHashMap.entrySet()) {
                    if (balanceList.containsKey(paidEntry.getKey())) {
                        // if balance list exist for the user, then get previous balance and update with paid amount
                        balanceList.replace(paidEntry.getKey(), balanceList.get(paidEntry.getKey()) - paidEntry.getValue());
                    } else {
                        // cal. balance list for the user first time
                        balanceList.put(paidEntry.getKey(), -paidEntry.getValue());
                    }
                }

                for (Map.Entry<String, Double> oweEntry : oweHashMap.entrySet()) {
                    if (balanceList.containsKey(oweEntry.getKey())) {
                        // if balance list exist for the user, then get previous balance and update with owe amount
                        balanceList.replace(oweEntry.getKey(), balanceList.get(oweEntry.getKey()) + oweEntry.getValue());
                    } else {
                        // cal. balance list for the user first time
                        balanceList.put(oweEntry.getKey(), oweEntry.getValue());
                    }
                }
            }

    //        for(Map.Entry<String, Double> entry: balanceList.entrySet()) {
    //            System.out.println(entry.getKey() + ": " + entry.getValue());
    //        }

            // giver, receiver list
            HashMap<String, Double> giverList = new HashMap<>();
            HashMap<String, Double> receiverList = new HashMap<>();

            // divide the users in receiver and giver, based on the balance

            decimalFormat.setRoundingMode(RoundingMode.CEILING);
            for (Map.Entry<String, Double> userBalance : balanceList.entrySet()) {
                if (userBalance.getValue() < 0) {
                    receiverList.put(userBalance.getKey(), Double.valueOf(decimalFormat.format(Math.abs(userBalance.getValue()))));
                } else {
                    giverList.put(userBalance.getKey(), Double.valueOf(decimalFormat.format(userBalance.getValue())));
                }
            }

            // sort in ascending order of value
            HashMap<String, Double> sortedReceiverMap = sortByValue(receiverList);
            HashMap<String, Double> sortedGiverMap = sortByValue(giverList);

            System.out.println("receiver list ---------------- ");
            for (Map.Entry<String, Double> list : sortedReceiverMap.entrySet()) {
                System.out.println(list.getKey() + ": " + list.getValue());
            }

            System.out.println("giver list ---------------- ");
            for (Map.Entry<String, Double> list : sortedGiverMap.entrySet()) {
                System.out.println(list.getKey() + ": " + list.getValue());
            }

            System.out.println("settle algorithm ---------------- ");

            // settle up List
            ArrayList<SettleUpItem> settleUpItems = new ArrayList<>();

            double receiverAmount, transferAmount, amount_to_be_given;

            for (Map.Entry<String, Double> giver : sortedGiverMap.entrySet()) {
                amount_to_be_given = giver.getValue();
                while (amount_to_be_given >= 0.1) {
                    System.out.println("amount to be given: " + amount_to_be_given);
                    String receiverID = sortedReceiverMap.keySet().iterator().next();
                    System.out.println("receiver ID: " + receiverID);
                    receiverAmount = sortedReceiverMap.get(receiverID);
                    transferAmount = Math.min(receiverAmount, amount_to_be_given);

                    sortedReceiverMap.replace(receiverID, receiverAmount - transferAmount);

                    if (sortedReceiverMap.get(receiverID) <= 0.1) {
                        System.out.println("Removing receiver: " + receiverID);
                        sortedReceiverMap.remove(receiverID);
                    }

                    amount_to_be_given = amount_to_be_given - transferAmount;
                    settleUpItems.add(new SettleUpItem(giver.getKey(), receiverID, transferAmount));
                }
            }

            System.out.println("transactions ---------------------------");
            for (SettleUpItem settleUpItem : settleUpItems) {
                System.out.println(settleUpItem.getGiverID() + "->" + settleUpItem.getReceiverID() + ": " + settleUpItem.getAmount());
            }

            // save settle up items in transaction table
            for (SettleUpItem settleUpItem : settleUpItems) {
                Transaction transaction = new Transaction();
                transaction.setGroupID(groupID);
                transaction.setPayerID(settleUpItem.getGiverID());
                transaction.setPayeeID(settleUpItem.getReceiverID());
                transaction.setAmount(settleUpItem.getAmount());
                this.transactionRepository.save(transaction);
            }

            // make group as settled=true in user service
            APIResponse response =  this.userService.settleGroup(groupID);
            apiResponse.setObject(response.getObject());
            apiResponse.setSuccess(response.getSuccess());
            return apiResponse;
        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setObject("Cannot Perform SettleUp!");
            return apiResponse;
        }
    }

    @Override
    public APIResponse showTransactionForGroup(String groupID) {
        APIResponse apiResponse = new APIResponse();
        try {
            Set<Transaction> transactionSet = this.transactionRepository.findTransactionByGroupID(groupID);
            apiResponse.setSuccess(true);
            apiResponse.setObject(transactionSet);
        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setObject("Could not fetch transactions!");
        }
        return apiResponse;
    }
}
