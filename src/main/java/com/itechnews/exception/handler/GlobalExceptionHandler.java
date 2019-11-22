//package com.itechnews.exception.handler;
//
//import com.itechnews.exception.ResourceNotFoundException;
//import org.springframework.dao.DataAccessException;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.servlet.NoHandlerFoundException;
//
//import java.nio.file.AccessDeniedException;
//import java.sql.SQLException;
//
////AOP
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(AccessDeniedException.class)
//    @ResponseStatus(value = HttpStatus.FORBIDDEN) // 403
//    public String handleAccessDeniedException(Exception ex) {
//        return "error/403";
//    }
//
//    @ExceptionHandler({NoHandlerFoundException.class, ResourceNotFoundException.class})
//    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
//    public String handleResourceNotFoundException(Exception ex) {
//        return "error/404";
//    }
//
////    @ExceptionHandler(value = NullPointerException.class)
////    @ResponseStatus(HttpStatus.BAD_REQUEST)
////    public String handleNullPointerException(Exception ex) {
////        System.out.println("A null pointer exception ocurred " + ex);
////        return "nullpointerExceptionPage";
////    }
//
//    //vd: http://cland.pro:8080/admin/category/edit/233232212-1
//    //không có id nào là 233232212 thì do lỗi sql không lấy được dữ liệu
////    @ExceptionHandler({SQLException.class, DataAccessException.class})
////    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //500
////    public String handleSQLException(Exception ex) {
////        return "error/500";
////    }
//
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST) //400
//    public String handleBadRequestException(Exception ex) {
//        return "error/400";
//    }
//
//}
