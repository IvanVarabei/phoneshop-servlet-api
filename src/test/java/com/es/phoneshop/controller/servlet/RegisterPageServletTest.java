package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.entity.User;
import com.es.phoneshop.model.service.UserService;
import com.es.phoneshop.model.service.impl.DefaultUserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegisterPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private final UserService userService = DefaultUserService.getInstance();
    @InjectMocks
    private final RegisterPageServlet servlet = new RegisterPageServlet();

    @Before
    public void setup() {
        when(request.getParameter("login")).thenReturn("ivan");
        when(request.getParameter("password")).thenReturn("qwerty");
        when(request.getParameter("repeatPassword")).thenReturn("qwerty");
        when(userService.ifLoginExist("ivan")).thenReturn(false);
        when(request.getRequestDispatcher("WEB-INF/pages/register.jsp")).thenReturn(requestDispatcher);
    }

    @Test
    public void doPostNoErrorsSaveUser() throws ServletException, IOException {
        servlet.doPost(request, response);

        verify(userService).save(new User("ivan", "qwerty", User.Role.CLIENT));
        verify(response).sendRedirect("index.jsp");
    }

    @Test
    public void doPostPasswordNotEqRepeatPassword() throws ServletException, IOException {
        when(request.getParameter("repeatPassword")).thenReturn("qwerty1");

        servlet.doPost(request, response);

        verify(request).setAttribute("error", "You entered different passwords.");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void doPostLoginAlreadyExistsInStorage() throws ServletException, IOException {
        when(userService.ifLoginExist("ivan")).thenReturn(true);

        servlet.doPost(request, response);

        verify(request).setAttribute("error", "Such a user already exists. Try another login.");
        verify(requestDispatcher).forward(request, response);
    }
}
