package org.techtown.mealproject;

import java.util.ArrayList;
import java.util.List;

public class DatabaseName {
    public static String DATABASE_NAME = "planner.db";
    public static String TABLE_PLANNER = "planner";

    public static String TABLE_SUN_BRE = "SunBre";
    public static String TABLE_SUN_LUN = "SunLun";
    public static String TABLE_SUN_DIN = "SunDin";
    public static String TABLE_MON_BRE = "MonBre";
    public static String TABLE_MON_LUN = "MonLun";
    public static String TABLE_MON_DIN = "MonDin";
    public static String TABLE_TUE_BRE = "TueBre";
    public static String TABLE_TUE_LUN = "TueLun";
    public static String TABLE_TUE_DIN = "TueDin";
    public static String TABLE_WED_BRE = "WedBre";
    public static String TABLE_WED_LUN = "WedLun";
    public static String TABLE_WED_DIN = "WedDin";
    public static String TABLE_THU_BRE = "ThuBre";
    public static String TABLE_THU_LUN = "ThuLun";
    public static String TABLE_THU_DIN = "ThuDin";
    public static String TABLE_FRI_BRE = "FriBre";
    public static String TABLE_FRI_LUN = "FriLun";
    public static String TABLE_FRI_DIN = "FriDin";

    public static ArrayList<String> TABLE = new ArrayList<>(List.of(TABLE_PLANNER,
            TABLE_SUN_BRE, TABLE_SUN_LUN, TABLE_SUN_DIN,
            TABLE_MON_BRE, TABLE_MON_LUN, TABLE_MON_DIN,
            TABLE_TUE_BRE, TABLE_TUE_LUN, TABLE_TUE_DIN,
            TABLE_WED_BRE, TABLE_WED_LUN, TABLE_WED_DIN,
            TABLE_THU_BRE, TABLE_THU_LUN, TABLE_THU_DIN,
            TABLE_FRI_BRE, TABLE_FRI_LUN, TABLE_FRI_DIN));
}
