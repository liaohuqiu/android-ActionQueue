package in.srain.cube.actionqueque;

public class ActionQueue {

    private boolean mLock = false;
    private Action<?> sHead;

    public synchronized void add(Action<?> action) {

        if (sHead == null) {
            sHead = action;
        } else {

            Action<?> head = sHead;
            if (head != null && head.equals(action)) {
                return;
            }

            while (head.mNext != null) {
                head = head.mNext;
                if (head != null && head.equals(action)) {
                    return;
                }
            }

            head.mNext = action;
        }

        tryToPopNext();
    }

    private synchronized void tryToPopNext() {
        if (sHead == null || mLock) {
            return;
        }
        mLock = true;

        Action<?> request = sHead;
        sHead = sHead.mNext;
        request.mNext = null;

        request.onAction();
    }

    public synchronized void notifyActionDoneThenTryToPopNext() {
        mLock = false;
        tryToPopNext();
    }

    public static abstract class Action<T> {

        protected T mBadge;
        private Action<?> mNext;

        public Action(T badge) {
            mBadge = badge;
        }

        public abstract void onAction();

        public T getBadge() {
            return mBadge;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || !(o instanceof Action)) {
                return false;
            }
            if (o == this) {
                return true;
            }
            return mBadge.equals(((Action<?>) o).mBadge);
        }
    }
}
