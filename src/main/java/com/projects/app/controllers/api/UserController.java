package com.projects.app.controllers.api;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.projects.app.commons.constants.ProfilePictureTypeEnum;
import com.projects.app.dto.UserProfileDTO;
import com.projects.app.commons.utils.FileUploader;
import com.projects.app.commons.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.projects.app.commons.dto.ApiResponse;
import com.projects.app.dto.UserDto;
import com.projects.app.services.UserService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    @Value("${project.image}")
    private String path;

    @Value("${user.profile.image.max-file-size-in-kb}")
    private int max_kb;


    // POST-create user
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    // PUT- update user
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer uid) {
        UserDto updatedUser = this.userService.updateUser(userDto, uid);
        return ResponseEntity.ok(updatedUser);
    }

    //ADMIN
    // DELETE -delete user
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid) {
        this.userService.deleteUser(uid);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully", true), HttpStatus.OK);
    }

    // GET - user get
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    // GET - user get
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }


    @PostMapping(value = "/upload-profile/{userId}/{type}", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadPostImage(@RequestParam("image") MultipartFile image,
                                             @PathVariable Integer userId,
                                             @PathVariable ProfilePictureTypeEnum type) throws Exception {


        Map<String, String> bytesToMb = Utils.ByteToMegabyte(String.valueOf(image.getSize()), max_kb);
        if (bytesToMb.get("message") == null) {
            UserProfileDTO userProfileDTO = new UserProfileDTO();
            userProfileDTO.setId(userId);
            UserProfileDTO user = this.userService.getUserProfileByUserId(userId);
            FileUploader fileUploader = new FileUploader(path);


            if (user.getProfilePicture() != null) {
                if (user.getProfilePicture().startsWith("images/")) {
                    fileUploader.deleteFile(user.getProfilePicture());
                }
            }
            if (type == ProfilePictureTypeEnum.BASE64) {
                userProfileDTO.setProfilePictureType(ProfilePictureTypeEnum.BASE64);

                userProfileDTO.setProfilePicture(fileUploader.getBase64(image));
            } else {
                userProfileDTO.setProfilePictureType(ProfilePictureTypeEnum.URL);
                userProfileDTO.setProfilePicture(path + fileUploader.uploadMultipartFile(image));
            }

            this.userService.createUser(userProfileDTO);
            return new ResponseEntity<>(userProfileDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(bytesToMb.get("message"), HttpStatus.OK);
        }


    }
}
