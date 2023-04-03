package uz.bprodevelopment.task.util;


import org.springframework.security.core.context.SecurityContextHolder;

public final class AppUtils {

    public static String getCurrentUsername(){
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
