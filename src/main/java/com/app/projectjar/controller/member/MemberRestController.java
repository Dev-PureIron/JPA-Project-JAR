package com.app.projectjar.controller.member;


import com.app.projectjar.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/members/*")
@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberService memberService;

    //   아이디 중복 검사
    @GetMapping("checkEmail")
    public Long checkId(@RequestParam("memberEmail") String memberEmail){
        return memberService.checkEmail(memberEmail);
    }

    //  닉네임 중복 검사
    @GetMapping("checkNickname")
    public Long checkNickname(@RequestParam("memberNickname") String memberNickname){
        return memberService.checkPhoneNumber(memberNickname);
    }

    //  휴대폰 번호 중복 검사
    @GetMapping("checkPhone")
    public Long checkPhone(@RequestParam("memberPhone") String memberPhone){
        return memberService.checkNickName(memberPhone);
    }

//    // 아이디 찾기
//    @PostMapping("send-id")
//    public RedirectView sendFindIdentifcationMail(String memberEmail) {
//        String memberIdentification = memberService.findIdentification(memberEmail);
//
//        if(memberIdentification == null){
//            return new RedirectView("/members/login");
//        }
//
//        MailVO mailVO = new MailVO();
//        mailVO.setAddress(memberEmail);
//        mailVO.setTitle("이웃집 반찬 아이디 찾기");
//        mailVO.setMessage("안녕하세요. 이웃집 반찬입니다.\n\n회원님의 아이디는 " + memberIdentification + "입니다.");
//
//        memberService.sendMail(mailVO);
//
//        return new RedirectView("/members/login");
//    }
//
//    // 비밀번호 찾기
//    @PostMapping("send-password")
//    public RedirectView sendFindPasswordMail(String memberEmail) {
//        String memberIdentification = memberService.findIdentification(memberEmail);
//
//        if(memberIdentification == null){
//            return new RedirectView("/members/login");
//        }
//
//        String randomkey = memberService.randomKey();
//        memberService.updateRandomKey(randomkey, memberEmail);
//
//        MailVO mailVO = new MailVO();
//        mailVO.setAddress(memberEmail);
//        mailVO.setTitle("이웃집 반찬 비밀번호 변경");
//        mailVO.setMessage("안녕하세요. 이웃집 반찬입니다.\n\n 비밀번호 변경을 위해선 아래 경로에 들어가주세요 \n\n " +
//                "url: http://localhost:10000/members/change-password?memberIdentification="+memberIdentification + "&memberRandomKey=" + Base64.getEncoder().encodeToString(randomkey.getBytes(StandardCharsets.UTF_8)));
//
//        memberService.sendMail(mailVO);
//
//        return new RedirectView("/members/login");
//    }

}