package app.service;

import app.cache.CartCache;
import app.dao.UserDAO;
import app.model.User;
import app.model.cart.Cart;
import app.model.enums.UserRole;
import app.model.enums.UserStatus;
import app.security.SecurityUser;
import app.util.AppUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    public static final Logger log = LoggerFactory.getLogger(UserService.class);

    @PersistenceContext
    private EntityManager entityManager;
    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDAO.findByEmail(email);
        if (user == null)
            throw new UsernameNotFoundException("Unknown user: " + email);
        return SecurityUser.fromUser(user);
    }

    @Override
    @Transactional
    public User findById(int userId) {
        return userDAO.findById(userId);
    }

    @Transactional
    @Override
    public User findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    @Transactional
    public List<User> allUsers() {
        return userDAO.findAll();
    }

    @Override
    @Transactional
    public boolean saveUser(User user, HttpServletRequest request) {
        User existUser = userDAO.findByEmail(user.getEmail());
        if (existUser != null) {
            AppUtils.setUserIdInSession(request, existUser.getId());
            log.info("User with login {} already exist.", user.getEmail());
            return false;
        }
        String password = user.getPassword();
        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.ACTIVE);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        Integer userId = userDAO.add(user);
        if (userId != null) {
            Cart cart = AppUtils.getCartFromSession(request);
            if (cart != null) {
                CartCache.getInstance().put(userId, cart);
            }
            AppUtils.setUserIdInSession(request, userId);
            try {
                request.login(user.getEmail(), password);
            } catch (ServletException e) {
                log.error(e.getMessage(), e);
            }
        }
        return userId != null;
    }


    @Transactional
    @Override
    public User edit(User user, HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User existUser = userDAO.findByEmail(user.getEmail());
        if (existUser != null) {
            log.info("User with login {} already exist.", user.getEmail());
        }
        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.ACTIVE);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        User editedUser = userDAO.edit(user);
        UserDetails userDetails = SecurityUser.fromUser(user);
        AppUtils.setUserIdInSession(request, user.getId());
        Authentication newAuth = new UsernamePasswordAuthenticationToken(userDetails, auth.getCredentials(), auth.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        return editedUser;
    }

    @Override
    public Date convertStringToDate(String date) {
        Date parsedDate = null;
        if (date != null) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                parsedDate = formatter.parse(date);
            } catch (ParseException e) {
                e.getStackTrace();
            }
        }
        return parsedDate;
    }
}
