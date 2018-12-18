package ga.eatup.user.mapper;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import ga.eatup.partner.domain.NoticeUploadVO;
import ga.eatup.user.domain.MenuVO;

public interface MenuMapper {

	public List<MenuVO> getMenu(@RequestParam("sno") int sno);
	
	public List<MenuVO> readMenuImg(int sno);
	
	
}
