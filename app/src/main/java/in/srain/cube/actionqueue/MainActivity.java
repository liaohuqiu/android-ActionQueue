package in.srain.cube.actionqueue;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import in.srain.cube.actionqueque.ActionQueue;

public class MainActivity extends Activity {

    ActionQueue mActionQueue = new ActionQueue();

    String[] mMessageList = new String[]{
            "message 1",
            "message 2",
            "message 3",
    };
    DialogInterface.OnDismissListener mOnDismissListener = new DialogInterface.OnDismissListener() {
        @Override
        public void onDismiss(DialogInterface dialog) {
            mActionQueue.notifyActionDoneThenTryToPopNext();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // using action queue to pop dialog
        if (id == R.id.action_show_dialog_with_queue) {

            Context context = this;
            for (int i = 0; i < mMessageList.length; i++) {
                String message = mMessageList[i];
                PopDialogAction action = new PopDialogAction(message);
                mActionQueue.add(action);
            }
            return true;
        }

        // pop dialog one by one, the one pop later will overlap the one popped previously.
        if (id == R.id.action_show_dialog_without_queue) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            for (int i = 0; i < mMessageList.length; i++) {
                String message = mMessageList[i];
                builder.setMessage(message).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class PopDialogAction extends ActionQueue.Action<String> {


        public PopDialogAction(String badge) {
            super(badge);
        }

        @Override
        public void onAction() {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            Dialog dialog = builder.setMessage(getBadge()).show();
            dialog.setOnDismissListener(mOnDismissListener);
        }
    }
}
