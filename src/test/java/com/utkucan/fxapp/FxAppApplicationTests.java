package com.utkucan.fxapp;
import com.utkucan.fxapp.common.ContainerTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class FxAppApplicationTests extends ContainerTestBase {

	@Test
	void contextLoads() {}

}
