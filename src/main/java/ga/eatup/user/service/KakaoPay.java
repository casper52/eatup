package ga.eatup.user.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import ga.eatup.user.domain.CartDTO;
import ga.eatup.user.domain.kakaopay.KakaoPayApprovalVO;
import ga.eatup.user.domain.kakaopay.KakaoPayReadyVO;
import lombok.extern.java.Log;

@Service
@Log
public class KakaoPay {

	private static List<CartDTO> finalCartList;
	
	private static final String HOST = "https://kapi.kakao.com";
	
	private KakaoPayReadyVO kakaoPayReadyVO;
	private KakaoPayApprovalVO kakaoPayApprovalVO;
	
	
	public String kakaoPayReady(int totalPrice, List<CartDTO> cartList) {
		
		finalCartList = cartList;

		RestTemplate restTemplate = new RestTemplate();

		// 서버로 요청할 Header
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "KakaoAK " + "21e1a296e2c3aabd8c183e424ead22b5");
		headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
		headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

		// 서버로 요청할 Body
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("cid", "TC0ONETIME");
		params.add("partner_order_id", "100");
		params.add("partner_user_id", "gorany");
		params.add("item_name", finalCartList.get(0).getSname()+"");
		params.add("quantity", finalCartList.get(0).getQuantity()+"");
		params.add("total_amount", totalPrice +"");
		params.add("tax_free_amount","0");
		params.add("approval_url", "http://localhost:8080/user/kakaopay/kakaoPaySuccess");
		params.add("cancel_url", "http://localhost:8080/user/kakaopay/kakaoPayCancel");
		params.add("fail_url", "http://localhost:8080/user/kakaopay/kakaoPaySuccessFail");
		
		log.info( "finalcartlist:" + finalCartList);

 		HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

		try {
			kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body, KakaoPayReadyVO.class);
			
			log.info("" + kakaoPayReadyVO);
			
			return kakaoPayReadyVO.getNext_redirect_pc_url();

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "/pay";
		
	}
	
	public Map<String, Object> kakaoPayInfo(String pg_token) {

		log.info("KakaoPayInfoVO............................................");
		log.info("-----------------------------");
		
		RestTemplate restTemplate = new RestTemplate();

		// 서버로 요청할 Header
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "KakaoAK " + "21e1a296e2c3aabd8c183e424ead22b5");
		headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
		headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

		// 서버로 요청할 Body
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("cid", "TC0ONETIME");
		params.add("tid", kakaoPayReadyVO.getTid());
		params.add("partner_order_id", "1001");
		params.add("partner_user_id", "gorany");
		params.add("pg_token", pg_token);
		params.add("total_amount", "2100");
		
		HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);
		Map<String, Object> map = new HashMap<>();
		
		try {
			kakaoPayApprovalVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), body, KakaoPayApprovalVO.class);
			log.info("" + kakaoPayApprovalVO);
			
			map.put("kakao", kakaoPayApprovalVO);
			map.put("orderList", finalCartList);
			
			return map;
		
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}
