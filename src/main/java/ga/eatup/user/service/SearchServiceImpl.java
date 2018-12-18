package ga.eatup.user.service;

import java.util.ArrayList;
import java.util.List;

import ga.eatup.user.domain.MenuVO;
import ga.eatup.user.domain.StoreVO;
import ga.eatup.user.mapper.SearchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Setter;
import lombok.extern.java.Log;

@Service
@Log
public class SearchServiceImpl implements SearchService{

	@Setter(onMethod_=@Autowired)
	private SearchMapper mapper;

	@Override
	public List<String> getName() {
		
		List<String> menuName = mapper.getMenuName();
		List<String> storeName = mapper.getStoreName();
		
		List<String> list = menuName;
		list.addAll(storeName);
		
		//중복제거
		List<String> resultList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
		    if (!resultList.contains(list.get(i))) {
		        resultList.add(list.get(i));
		    }
		}

		return resultList;
	}


	@Override
	public List<MenuVO> searchMenu(String keyword) {
		
		List<MenuVO> result = mapper.searchMenu(keyword);
		
		result.forEach(vo ->{
			int sno = vo.getSno();
			vo.setStoreVO(mapper.searchStoreWithSno(sno));
		});
		
		return result;
	}


	@Override
	public List<StoreVO> searchStore(String keyword) {
		
		List<StoreVO> result = mapper.searchStore(keyword);
		
		result.forEach(vo ->{
			int sno = vo.getSno();
			vo.setMenuList(mapper.searchMenuWithSno(sno));
		});
		
		return result;
	}

}
