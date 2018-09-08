package playmzubair.com.myalarmandroid;



public class Reminder_Modelclass {

    String time;
    String days;
    int btnVal;
    int requestCode;
    String reminder_type;

    public Reminder_Modelclass(String time, String days, int btnVal, int requestCode, String reminder_type) {
        this.time = time;
        this.days = days;
        this.btnVal = btnVal;
        this.requestCode=requestCode;
        this.reminder_type=reminder_type;
    }

    public Reminder_Modelclass() {
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public void setBtnVal(int btnVal) {
        this.btnVal = btnVal;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public String getTime() {
        return time;
    }

    public String getReminder_type() {
        return reminder_type;
    }

    public void setReminder_type(String reminder_type) {
        this.reminder_type = reminder_type;
    }

    public String getDays() {
        return days;
    }

    public int getBtnVal() {
        return btnVal;
    }

    public int getRequestCode(){
        return requestCode;
    }
}
