package com;

import com.common.mail.SendMail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 *
 * @author 程序猿DD
 * @version 1.0.0
 * @blog http://blog.fdway.com
 *
// */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ApplicationTests {

//	private MockMvc mvc;
//
//
//
    @Autowired
    private SendMail sendMail;

    @Test
	public void getHello() throws Exception {

            sendMail.sendSimpleEmail("1442079857@qq.com","tes1t","test1");
	}
//	@Test
//	public void getHello() throws Exception {
//		mvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk())
//				.andExpect(content().string(equalTo("Hello World")));
//	}
//
//	@Test
//	public void testUserController() throws Exception {
////  	测试UserController
//		RequestBuilder request = null;
//
//		// 1、get查一下user列表，应该为空
//		request = get("/users/");
//		mvc.perform(request)
//				.andExpect(status().isOk())
//				.andExpect(content().string(equalTo("[]")));
//
//		// 2、post提交一个user
//		request = post("/users/")
//				.param("id", "1")
//				.param("name", "测试大师")
//				.param("age", "20");
//		mvc.perform(request)
////				.andDo(MockMvcResultHandlers.print())
//				.andExpect(content().string(equalTo("success")));
//
//		// 3、get获取user列表，应该有刚才插入的数据
//		request = get("/users/");
//		mvc.perform(request)
//				.andExpect(status().isOk())
//				.andExpect(content().string(equalTo("[{\"id\":1,\"name\":\"测试大师\",\"age\":20}]")));
//
//		// 4、put修改id为1的user
//		request = put("/users/1")
//				.param("name", "测试终极大师")
//				.param("age", "30");
//		mvc.perform(request)
//				.andExpect(content().string(equalTo("success")));
//
//		// 5、get一个id为1的user
//		request = get("/users/1");
//		mvc.perform(request)
//				.andExpect(content().string(equalTo("{\"id\":1,\"name\":\"测试终极大师\",\"age\":30}")));
//
//		// 6、del删除id为1的user
//		request = delete("/users/1");
//		mvc.perform(request)
//				.andExpect(content().string(equalTo("success")));
//
//		// 7、get查一下user列表，应该为空
//		request = get("/users/");
//		mvc.perform(request)
//				.andExpect(status().isOk())
//				.andExpect(content().string(equalTo("[]")));
//
//	}

}
