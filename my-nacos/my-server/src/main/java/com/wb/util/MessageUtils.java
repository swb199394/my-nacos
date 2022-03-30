package com.wb.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @author ylx
 * @version 1.0
 * @date 2022/3/16 8:34
 */
public class MessageUtils {
    private static MessageSource messageSource = (MessageSource)SpringContextUtils.getBean("messageSource");

    public MessageUtils() {
    }

    public static String getMessage(int code) {
        return getMessage(code);
    }

    public static String getMessage(int code, String... params) {
        return messageSource.getMessage(code + "", params, LocaleContextHolder.getLocale());
    }
}
