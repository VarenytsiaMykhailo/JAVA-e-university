package com.github.varenytsiamykhailo.euniversity.web;

import javax.servlet.*;
import java.io.IOException;

/**
 *     default фильтр нужн для того, чтобы решить проблему подключения css, js и других ресурсов.
 *     Кажется AuthFilter (отвечающий за авторизацию пользователей),
 *     и замапленный к url-patter: "/*" блокирует получение ресурсов из статических директорий веб-приложения.
 *     Одним из решений проблемы (которое придумал я) было бы: в коде класса AuthFilter проверять запрашиваемый URL/URI.
 *     Распарсив адрес запроса можно определить, какой контент запрашивается.
 *     И если запрашиваются ресурсы с .css, .js и т.д. расширениями - пропускать валидацию фильтра
 *     вызовом filterChain.doFilter(req, res).
 *     Но это решение некрасивое и имеет много подводных камней, которые могут доставить неприятности:
 *     можно будет легко проходить фильтр без авторизации.
 *
 *     В интернете нашел вот такой красивый способ решения возникшей проблемы:
 *     http://www.kuligowski.pl/java/rest-style-urls-and-url-mapping-for-static-content-apache-tomcat,5
 *
 *     Оно основано на реализации default фильтра, который будет обрабатывать все запросы к ресурсам, находящимся в директории static.
 *     default фильтр будет обрабатывать все запросы к static/* вместо AuthFilter.
 *     default фильтр всего лишь передает запрос default сервлету, который предоставляет доступ ко всем файлам веб-приложения, в том числе и к директории static.
 *     Минус данного варианта решения проблемы: можно получать любые ресурсы (картинки, код и т.д.) в static/... без прохождения авторизации.
 */
public class DefaultFilter implements Filter {

    private RequestDispatcher defaultRequestDispatcher;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.defaultRequestDispatcher = filterConfig.getServletContext().getNamedDispatcher("default");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        defaultRequestDispatcher.forward(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
