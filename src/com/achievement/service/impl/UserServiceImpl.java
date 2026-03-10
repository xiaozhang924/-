/**
 * UserServiceImpl.java
 * 该类用于实现用户数据的业务逻辑操作
 */
package com.achievement.service.impl;

import com.achievement.dao.UserDAO;
import com.achievement.dao.impl.UserDAOImpl;
import com.achievement.exception.PasswordMismatchException;
import com.achievement.exception.UsernameExistsException;
import com.achievement.exception.UserNotFoundException;
import com.achievement.exception.ValidationException;
import com.achievement.model.User;
import com.achievement.service.UserService;
import com.achievement.util.PasswordUtil;
import com.achievement.util.ValidationUtil;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    /**
     * 默认构造方法
     * <p>初始化UserServiceImpl对象，使用默认的UserDAOImpl实例</p>
     */
    public UserServiceImpl() {
        this.userDAO = new UserDAOImpl();
    }

    /**
     * 构造方法
     * <p>初始化UserServiceImpl对象，使用指定的UserDAO实例（用于测试或依赖注入）</p>
     * 
     * @param userDAO UserDAO接口的实现类实例
     */
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * 用户注册
     * <p>完成用户注册的业务逻辑，包括输入验证、用户名唯一性检查、密码哈希处理和用户信息保存</p>
     * 
     * @param user 用户对象，包含注册所需的所有信息
     * @throws ValidationException 当输入验证失败时抛出
     * @throws UsernameExistsException 当用户名已存在时抛出
     */
    @Override
    public void register(User user) {
        // 输入验证
        if (!ValidationUtil.isValidUsername(user.getUsername())) {
            throw new ValidationException("用户名格式错误，长度3-20个字符，只能包含字母、数字和下划线");
        }
        
        if (!ValidationUtil.isValidPassword(user.getPassword())) {
            throw new ValidationException("密码格式错误，长度6-20个字符");
        }
        
        if (user.getEmail() != null && !user.getEmail().isEmpty() && !ValidationUtil.isValidEmail(user.getEmail())) {
            throw new ValidationException("邮箱格式错误");
        }
        
        if (user.getPhone() != null && !user.getPhone().isEmpty() && !ValidationUtil.isValidPhone(user.getPhone())) {
            throw new ValidationException("手机号码格式错误");
        }
        
        // 检查用户名是否已存在
        if (getUserByUsername(user.getUsername()) != null) {
            throw new UsernameExistsException();
        }
        
        // 对密码进行哈希处理
        String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
        
        userDAO.addUser(user);
    }

    /**
     * 用户登录
     * <p>验证用户登录信息，检查用户名和密码是否匹配</p>
     * 
     * @param username 用户名
     * @param password 密码
     * @return 登录成功的用户对象
     * @throws UserNotFoundException 当用户不存在时抛出
     * @throws PasswordMismatchException 当密码不匹配时抛出
     */
    @Override
    public User login(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        if (user == null) {
            throw new UserNotFoundException();
        }
        // 验证密码
        if (!PasswordUtil.verifyPassword(password, user.getPassword())) {
            throw new PasswordMismatchException();
        }
        return user;
    }

    /**
     * 根据用户ID获取用户信息
     * <p>通过用户ID查询用户的详细信息</p>
     * 
     * @param id 用户的唯一标识符
     * @return 用户对象，如果未找到则返回null
     */
    @Override
    public User getUserById(Integer id) {
        return userDAO.getUserById(id);
    }

    /**
     * 根据用户名获取用户信息
     * <p>通过用户名查询用户的详细信息</p>
     * 
     * @param username 用户名
     * @return 用户对象，如果未找到则返回null
     */
    @Override
    public User getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    /**
     * 根据院系获取学生列表
     * <p>查询指定院系的所有学生信息</p>
     * 
     * @param department 院系名称
     * @return 学生列表，如果没有记录则返回空列表
     */
    @Override
    public List<User> getStudentsByDepartment(String department) {
        return userDAO.getStudentsByDepartment(department);
    }

    /**
     * 根据专业获取学生列表
     * <p>查询指定专业的所有学生信息</p>
     * 
     * @param major 专业名称
     * @return 学生列表，如果没有记录则返回空列表
     */
    @Override
    public List<User> getStudentsByMajor(String major) {
        return userDAO.getStudentsByMajor(major);
    }

    /**
     * 根据年级获取学生列表
     * <p>查询指定年级的所有学生信息</p>
     * 
     * @param grade 年级信息
     * @return 学生列表，如果没有记录则返回空列表
     */
    @Override
    public List<User> getStudentsByGrade(String grade) {
        return userDAO.getStudentsByGrade(grade);
    }

    /**
     * 根据多条件获取学生列表
     * <p>支持按姓名、院系、专业、年级组合查询学生信息</p>
     * 
     * @param name 学生姓名（模糊匹配）
     * @param department 院系名称
     * @param major 专业名称
     * @param grade 年级信息
     * @return 符合条件的学生列表，如果没有记录则返回空列表
     */
    @Override
    public List<User> getStudentsByConditions(String name, String department, String major, String grade) {
        return userDAO.getStudentsByConditions(name, department, major, grade);
    }

    /**
     * 更新用户信息
     * <p>修改用户的详细信息</p>
     * 
     * @param user 包含更新信息的用户对象
     */
    @Override
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    /**
     * 删除用户
     * <p>根据用户ID删除用户记录</p>
     * 
     * @param id 用户的唯一标识符
     */
    @Override
    public void deleteUser(Integer id) {
        userDAO.deleteUser(id);
    }
}
