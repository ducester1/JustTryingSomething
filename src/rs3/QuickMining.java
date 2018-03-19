package rs3;

import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt6.ClientContext;
import rs3.Tasks.Drop;
import rs3.Tasks.Mine;

import java.util.ArrayList;
import java.util.List;

@Script.Manifest(name ="QuickMining", description ="Turorial", properties = "client=6; author=Scott; topic=999")

public class QuickMining extends PollingScript<ClientContext> {

    List<Task> taskList = new ArrayList<Task>();

    @Override
    public void start(){
        taskList.add(new Drop(ctx));
        taskList.add(new Mine(ctx));
    }

    @Override
    public void poll() {

        for(Task task : taskList){
            //breaks if pressed stop
            if(ctx.controller.isStopping()){
                break;
            }

            if(task.activate()){
                task.execute();
                break;
            }
        }
    }
}
