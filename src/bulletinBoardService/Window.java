package bulletinBoardService;

public class Window {
    private class UITasksImpl implements UITasks {
        private Messenger messenger = null;
        //UI
        @Override
        public String getMessage() {
            String res = textFieldMsg.getText();
            textFieldMsg.setText("");
            return res;
        }
        @Override
        public void setText(String txt) {
            textArea.append(txt + "\n");
        }
    }

}
