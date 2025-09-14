package com.learn.erp.rabbitconfig;

public class RabbitConstants {
	
    // Exchange
    public static final String AUTH_EXCHANGE = "auth.exchange";
    public static final String ATTENDANCE_EXCHANGE = "attendance.exchange";
    public static final String EMPLOYEE_EXCHANGE = "employee.exchange";
    
    // Queues  markAbsences
    public static final String USER_REGISTERED_QUEUE = "auth.user.registered.queue";
    public static final String PASSWORD_RESET_QUEUE = "auth.password.reset.queue";
    public static final String PASSWORD_CODE_REGENERATED_QUEUE = "auth.password.code.regenerated.queue";
    public static final String ATTENDANCE_MARK_ABSENCES_QUEUE = "attendance.mark.absences.queue";
    public static final String ADD_NEW_EMPLOYEE_QUEUE = "employee.add.new.queue";

    // Routing Keys
    public static final String USER_REGISTERED_KEY = "auth.user.registered";
    public static final String PASSWORD_RESET_KEY = "auth.password.reset";
    public static final String PASSWORD_CODE_REGENERATED_KEY = "auth.password.code.regenerated";
    public static final String ATTENDANCE_MARK_ABSENCES_KEY = "attendance.mark.absences";
    public static final String NEW_EMPLOYEE_KEY = "employee.new";

}
