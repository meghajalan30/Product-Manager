package net.codejava.services;

import net.codejava.dto.UserRegistrationDto;
import net.codejava.model.UserInfo;
import net.codejava.repository.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {

   /* @InjectMocks
    private UserService userService1;
*/

    /*@Mock
    private UserRepository userRepository;*/

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRegistrationCheck() {
        UserRegistrationDto dto=new UserRegistrationDto();
        dto.setUsername("username");
        dto.setPassword("password");
        dto.setConfirmPassword("password");
        dto.setRole("USER");
        List<UserInfo> userList=new ArrayList<>();
        userList.add(new UserInfo("megha","megha","ADMIN"));
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll()).thenReturn(userList);
        UserService service=new UserService();
        service.setRepo(userRepository);
        String user= service.registrationCheck(dto);
        assertEquals("valid",user);
    }

    @Test
    void testSave(){
        UserRegistrationDto dto=new UserRegistrationDto();
        dto.setUsername("user");
        dto.setPassword("user");
        dto.setConfirmPassword("user");
        dto.setRole("ADMIN");
        UserInfo user=new UserInfo("user","user","ADMIN");
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(any(UserInfo.class))).thenReturn(user);
        UserService service=new UserService();
        service.setRepo(userRepository);
        UserInfo userInf=service.save(dto);
        assertEquals(userInf,user);
    }

     @Test
     void testGetUserInfoByUserName() {
         UserInfo userInfo=new UserInfo();
         userInfo.setUsername("megha");
         userInfo.setPassword("megha");
         userInfo.setRole("ADMIN");
         UserRepository userRepository = mock(UserRepository.class);
         //UserInfo userInfo1=Mockito.mock(UserInfo.class);
         when(userRepository.findById("megha")).thenReturn(Optional.of(userInfo));
         UserService service=new UserService();
         service.setRepo(userRepository);
         Optional<UserInfo> user= service.getUserInfoByUserName("megha");
         assertEquals(Optional.of(userInfo),user);
         System.out.println(user);
         //userService1 = Mockito.mock(UserService.class);
    }

}
