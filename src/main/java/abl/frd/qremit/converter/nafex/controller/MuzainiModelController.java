package abl.frd.qremit.converter.nafex.controller;
import abl.frd.qremit.converter.nafex.helper.MuzainiModelServiceHelper;
import abl.frd.qremit.converter.nafex.helper.MyUserDetails;
import abl.frd.qremit.converter.nafex.model.FileInfoModel;
import abl.frd.qremit.converter.nafex.model.User;
import abl.frd.qremit.converter.nafex.service.CommonService;
import abl.frd.qremit.converter.nafex.service.MuzainiModelService;
import abl.frd.qremit.converter.nafex.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class MuzainiModelController {
    private final MuzainiModelService muzainiModelService;
    private final MyUserDetailsService myUserDetailsService;
    private final CommonService commonService;

    @Autowired
    public MuzainiModelController(MuzainiModelService muzainiModelService, MyUserDetailsService myUserDetailsService, CommonService commonService){
        this.muzainiModelService = muzainiModelService;
        this.myUserDetailsService = myUserDetailsService;
        this.commonService = commonService;
    }

    @PostMapping("/muzainiUpload")
    public String uploadFile(@AuthenticationPrincipal MyUserDetails userDetails, @ModelAttribute("file") MultipartFile file, Model model) {
        model.addAttribute("exchangeMap", myUserDetailsService.getLoggedInUserMenu(userDetails));
        int userId = 000000000;
        // Getting Logged In user Details in this block
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            MyUserDetails myUserDetails = (MyUserDetails)authentication.getPrincipal();
            User user = myUserDetails.getUser();
            userId = user.getId();
        }
        String message = "";
        FileInfoModel fileInfoModelObject;
        if (commonService.hasCSVFormat(file)) {
            if(!commonService.ifFileExist(file)){
                try {
                    fileInfoModelObject = muzainiModelService.save(file, userId);
                    model.addAttribute("fileInfo", fileInfoModelObject);
                } catch (IllegalArgumentException e) {
                    message = e.getMessage();
                }
                catch (Exception e) {
                    message = "Could not upload the file: " + file.getOriginalFilename() +"";
                }
            }
            message = "File with the same name already exists !!";
        }
        message = "Please upload a csv file!";
        model.addAttribute("message", message);
        return commonService.uploadSuccesPage;
    }

}
