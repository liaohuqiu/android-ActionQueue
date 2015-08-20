ActionQueue allows you run action one by one.

#### Import

Repositories:

```groovy
allprojects {
    repositories {
        mavenCentral()
        maven {
            url "https://oss.sonatype.org/content/repositories/snapshots"
        }
        jcenter()
    }
}
```

Add to dependencies:

```groovy
compile 'in.srain.cube:action-queue:1.0.1'
```

#### Usage

* create actions

    ```java
    String[] messageList = new String[]{
            "message 1",
            "message 2",
            "message 3",
    };
    for (int i = 0; i < messageList.length; i++) {
        String message = messageList[i];
        PopDialogAction action = new PopDialogAction(message);
        mActionQueue.add(action);
    }
    ```

* process action 

    ```java
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
    ```
