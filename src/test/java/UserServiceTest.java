

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cidic.fontdesign.model.User;
import com.cidic.fontdesign.service.UserService;
import com.cidic.fontdesign.util.CipherUtil;

import static java.lang.System.out;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class UserServiceTest {
	
	@Autowired
	@Qualifier(value="userServiceImpl")
    private UserService userServiceImpl;  
     
    @Test
    public  void testInsertUser(){
    	
    	if (userServiceImpl.checkUserName("cidic")){
    		out.println("username is used");
    	}
    	else{
    		User user = new User();
        	user.setUsername("cidic");
        	user.setPassword(CipherUtil.generatePassword("cidiccidic"));
        	userServiceImpl.insertUser(user);
    	}
    }
    
   // @Test
    public void testCheckUsername(){
    	boolean result = userServiceImpl.checkUserName("liling");
    	out.println(result);
    }
    
    //@Test
    public void testCheckUser(){
    	User user = new User();
    	user.setUsername("tangyin");
    	user.setPassword(CipherUtil.generatePassword("123456"));
    	out.println(userServiceImpl.checkUser(user));
    }
}
