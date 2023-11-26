package com.example.b07project.ui.announcements.view;

public final class FormatDate {
    public static String formatDate(String date){
        /*
        Given a date formatted as yyyy,MM,dd,hh,mm,ss, returns a date formatted as "Month. dd, yyyy".
        Example: "2023,11,01,17,20,30" -> "Nov. 01, 2023"
         */

        //dateArr format: [year, month (number), date, hour, minute, second];
        String[] dateArr = date.split(",");
        String month = monthNumToStr(Integer.parseInt(dateArr[1]) - 1);
        return (month + " " + dateArr[2] + ", " + dateArr[0] + " at " + dateArr[3] + ":" + dateArr[4]);
    }

    private static String monthNumToStr(int idx) {
        //Converts month number to its respective abbreviated name
        String[] months = {"Jan.", "Feb.", "Mar.", "Apr.", "May",
                "Jun.", "Jul.", "Aug.", "Sep.", "Oct.", "Nov.", "Dec."};
        return months[idx];
    }
}
