package TheSmith.Tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;

import java.util.concurrent.Callable;

public class Bank extends TheSmith.Task {

    private String[] choice;
    private int[] itemsFromBank = {0, 0};
    private int itemId;

    public Bank(ClientContext ctx, String[] choice) {
        super(ctx);
        this.choice = choice;
    }

    @Override
    public boolean activate() {
        return ctx.backpack.select().id(2349).count() == 14 || ctx.backpack.select().count() == 0 ||
                ctx.backpack.select().id(436).count() < 1 || ctx.backpack.select().id(438).count() < 1;
    }

    @Override
    public void execute() {
        if (ctx.bank.opened()) {
            final int inventCount = ctx.backpack.select().count();
            if (inventCount > 0) {
                if (ctx.bank.depositInventory()) {

                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return ctx.backpack.select().count() != inventCount;
                        }
                    }, 250, 20);
                }
            }
            if (inventCount == 0) {
                if (ctx.bank.select().id(436).count() == 0 || ctx.bank.select().id(438).count() == 0) {
                    ctx.controller.stop();
                }
                ctx.bank.withdraw(436, 14);
                ctx.bank.withdraw(438, 14);
                ctx.bank.close();
            }
        } else {
            if (ctx.bank.inViewport() && ctx.players.local().speed() == 0) {
                if (ctx.bank.open()) {
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return ctx.bank.opened();
                        }
                    }, 250, 20);
                }
            } else {
                ctx.camera.turnTo(ctx.bank.nearest(), 25);
            }
        }
    }

    private void WhatToDo() {
        //switch welke bar soort
        switch (choice[1]) {
            case "Bronze": {
                //switch welk item
                switch (choice[2]) {
                    case "Bar": {

                        break;
                    }
                    case "Platebody": {
                        itemsFromBank[0] = 2349;
                        itemsFromBank[1] = 0;
                        itemId = 1117;
                        break;
                    }
                }
            }
        }
    }
}
