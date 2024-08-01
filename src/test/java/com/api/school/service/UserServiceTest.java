package com.api.school.service;

import com.api.school.User;
import com.api.school.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void buscarTodos() {
        User user1 = new User("1", "Jose", 18, 2222222, "jose@email.com", "password", null);
        User user2 = new User("2", "Jhon", 19, 3333333, "jhon@email.com", "password", null);

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<UserDto> users = userService.getAll();

        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals("Jose", users.get(0).getName());
        assertEquals("Jhon", users.get(1).getName());
    }

    @Test
    public void FindAll() {
        userService.getAll();
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void ListNoNull() {
        when(userRepository.findAll()).thenReturn(List.of());
        List<UserDto> users = userService.getAll();
        assertNotNull(users);
    }

    @Test
    public void ListDelTamañoCorrecto() {
        User user1 = new User("1", "Jose", 18, 2222222, "jose@email.com", "password", null);
        User user2 = new User("2", "Jhon", 19, 3333333, "jhon@email.com", "password", null);

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<UserDto> users = userService.getAll();

        assertEquals(2, users.size());
    }


    @Test
    public void buscarPorId() {
        User user = new User("1", "Jose", 18, 2222222, "jose@email.com", "password", null);

        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        UserDto userDto = userService.getById("1");

        assertNotNull(userDto);
        assertEquals("1", userDto.getId());
        assertEquals("Jose", userDto.getName());
        assertEquals(18, userDto.getAge());
        assertEquals(2222222, userDto.getTell());
        assertEquals("jose@email.com", userDto.getEmail());
    }

    @Test
    public void IdNoExistente() {
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        UserDto userDto = userService.getById("1");

        assertNull(userDto);
    }

    @Test
    public void IdNoNull() {
        User user = new User("1", "Jose", 18, 2222222, "jose@email.com", "password", null);

        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        UserDto userDto = userService.getById("1");

        assertNotNull(userDto);
    }

    @Test
    public void IdNull() {
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        UserDto userDto = userService.getById("1");

        assertNull(userDto);
    }


    @Test
    public void FindById() {
        userService.getById("1");
        verify(userRepository, times(1)).findById("1");
    }


    @Test
    public void buscarPorEmail() {
        User user = new User("1", "Jose", 18, 2222222, "jose@email.com", "password", null);

        when(userRepository.findByEmail("jose@email.com")).thenReturn(Optional.of(user));

        UserDto userDto = userService.getByEmail("jose@email.com");

        assertNotNull(userDto);
        assertEquals("1", userDto.getId());
    }

    @Test
    public void ListVacia() {
        when(userRepository.findAll()).thenReturn(List.of());

        List<UserDto> users = userService.getAll();

        assertNotNull(users);
        assertTrue(users.isEmpty());
    }

    @Test
    public void guardar() {
        UserDto userDto = new UserDto(null, "Jose", 18, 2222222, "jose@email.com", "password", null);
        User user = new User("1", "Jose", 18, 2222222, "jose@email.com", "password", null);

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto savedUserDto = userService.save(userDto);

        assertNotNull(savedUserDto);
        assertEquals("1", savedUserDto.getId());
        assertEquals("Jose", savedUserDto.getName());
        assertEquals(18, savedUserDto.getAge());
        assertEquals(2222222, savedUserDto.getTell());
        assertEquals("jose@email.com", savedUserDto.getEmail());
    }

    @Test
    public void ExcepcionGuardar() {
        UserDto userDto = new UserDto(null, null, 18, 2222222, null, "password", null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.save(userDto);
        });

        assertEquals("User name and email cannot be null", exception.getMessage());
    }

    @Test
    public void SaveUser() {
        UserDto userDto = new UserDto(null, "Jose", 18, 2222222, "jose@email.com", "password", null);
        User user = new User("1", "Jose", 18, 2222222, "jose@email.com", "password", null);

        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.save(userDto);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void IdNotNull() {
        UserDto userDto = new UserDto(null, "Jose", 18, 2222222, "jose@email.com", "password", null);
        User user = new User("1", "Jose", 18, 2222222, "jose@email.com", "password", null);

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto savedUserDto = userService.save(userDto);

        assertNotNull(savedUserDto.getId());
    }

    @Test
    public void SaveUserC() {
        UserDto userDto = new UserDto(null, "Jose", 18, 2222222, "jose@email.com", "password", null);
        User user = new User("1", "Jose", 18, 2222222, "jose@email.com", "password", null);

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto savedUserDto = userService.save(userDto);

        assertEquals("Jose", savedUserDto.getName());
        assertEquals(18, savedUserDto.getAge());
        assertEquals(2222222, savedUserDto.getTell());
        assertEquals("jose@email.com", savedUserDto.getEmail());
    }


    @Test
    public void NoSobrescribir() {
        UserDto userDto = new UserDto("2", "Jose", 18, 2222222, "jose@email.com", "password", null);
        User user = new User("1", "Jose", 18, 2222222, "jose@email.com", "password", null);

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto savedUserDto = userService.save(userDto);

        assertNotNull(savedUserDto);
        assertEquals("1", savedUserDto.getId());
    }

    @Test
    public void UpdateUser() {
        User user = new User("1", "Jose", 18, 2222222, "jose@email.com", "password", null);
        UserDto userDto = new UserDto("1", "Jose Updated", 20, 2222222, "jose_updated@email.com", "password", null);

        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto updatedUserDto = userService.update(userDto, "1");

        assertNotNull(updatedUserDto);
        assertEquals("Jose Updated", updatedUserDto.getName());
        assertEquals(20, updatedUserDto.getAge());
        assertEquals("jose_updated@email.com", updatedUserDto.getEmail());
    }

    @Test
    public void UpdateExistente() {
        UserDto userDto = new UserDto("1", "Jose", 18, 2222222, "jose@email.com", "password", null);

        when(userRepository.findById("1")).thenReturn(Optional.empty());

        UserDto updatedUserDto = userService.update(userDto, "1");

        assertNull(updatedUserDto);
    }

    @Test
    public void UpdateFindById() {
        UserDto userDto = new UserDto("1", "Jose", 18, 2222222, "jose@email.com", "password", null);
        userService.update(userDto, "1");
        verify(userRepository, times(1)).findById("1");
    }

    @Test
    public void UpdateSave() {
        User user = new User("1", "Jose", 18, 2222222, "jose@email.com", "password", null);
        UserDto userDto = new UserDto("1", "Jose Updated", 20, 2222222, "jose_updated@email.com", "password", null);

        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.update(userDto, "1");

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void ValidarUpdate() {
        UserDto userDto = new UserDto("1", null, 18, 2222222, "jose@email.com", "password", null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.update(userDto, "1");
        });

        String expectedMessage = "User name and email cannot be null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void Eliminar() {
        doNothing().when(userRepository).deleteById("1");

        userService.delete("1");

        verify(userRepository, times(1)).deleteById("1");
    }

    @Test
    public void ExeptionDelete() {
        doNothing().when(userRepository).deleteById("1");

        assertDoesNotThrow(() -> userService.delete("1"));
    }

    @Test
    public void CallDeleteById() {
        doNothing().when(userRepository).deleteById("1");

        userService.delete("1");

        verify(userRepository, times(1)).deleteById("1");
    }

    @Test
    public void CallDeleteByIdC() {
        doNothing().when(userRepository).deleteById("1");

        userService.delete("1");

        verify(userRepository).deleteById(argThat(id -> id.equals("1")));
    }

    /*@Test
    public void eliminarDebeLanzarExcepcionSiIdEsNulo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.delete(null);
        });

        String expectedMessage = "Id cannot be null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }*/

}



