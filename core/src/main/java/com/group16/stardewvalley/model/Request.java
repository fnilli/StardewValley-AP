package com.group16.stardewvalley.model;

import com.group16.stardewvalley.model.items.Item;
import java.util.concurrent.atomic.AtomicInteger;

public class Request implements notification{
    private static final AtomicInteger nextId = new AtomicInteger(1);
    private final int Id;
    private final String requesterName;
    private final Item requestedItem;
    private final int requestedAmount;
    private final Item offeredItem;
    private final int offeredItemAmount;
    private boolean acceptStatus;
    private final int offeredCoin;
    private boolean isDone;  //برای ماموریت های NPC فقط اولین پلیر

    public Request(String requesterName, Item requestedItem, int requestedAmount,
                   Item offeredItem, int offeredItemAmount, int offeredCoin) {
        this.Id = nextId.getAndIncrement();
        this.requesterName = requesterName;
        this.requestedItem = requestedItem;
        this.requestedAmount = requestedAmount;
        this.offeredItem = offeredItem;
        this.offeredItemAmount = offeredItemAmount;
        this.acceptStatus = false;
        this.offeredCoin = offeredCoin;
        isDone = false;
    }

    public boolean isDone() {
        return isDone;
    }

    public int getOfferedCoin() {
        return offeredCoin;
    }

    public String getMessage() {
        String baseMessage;

        if (isTradeOffer()) {
            baseMessage = String.format(
                    "Request #%d: %s wants to trade %d x %s for %d x %s",
                    Id, requesterName, requestedAmount, requestedItem.getName(),
                    offeredItemAmount, offeredItem.getName()
            );
        } else {
            baseMessage = String.format(
                    "Request #%d: %s requests %d x %s",
                    Id, requesterName, requestedAmount, requestedItem.getName()
            );
        }

        if (acceptStatus) {
            baseMessage += " and has been accepted by you";
        }

        return baseMessage;
    }

    public boolean isTradeOffer() {
        return offeredItem != null && offeredItemAmount > 0;
    }

    public int getId() {
        return Id;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public Item getRequestedItem() {
        return requestedItem;
    }

    public int getRequestedAmount() {
        return requestedAmount;
    }

    public Item getOfferedItem() {
        return offeredItem;
    }

    public int getOfferedItemAmount() {
        return offeredItemAmount;
    }

    public boolean isAccepted() {
        return acceptStatus;
    }

    public void accept() {
        this.acceptStatus = true;
    }

    public void reject() {
        this.acceptStatus = false;
    }

}