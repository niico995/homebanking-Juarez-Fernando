package com.mindhub.homebanking;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.mindhub.homebanking.utils.GerenerateRandom;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@SpringBootTest
class HomebankingApplicationTests {

	@Test
	void contextLoads() {
	}

	@RepeatedTest(15)
	void testCVV() {
		int cvv = GerenerateRandom.cvv();
		String cvvString = String.valueOf(cvv);
		assertThat(cvvString.length(), lessThanOrEqualTo(3));
	}
	@RepeatedTest(15)
	void testNumber() {
		int cvv = GerenerateRandom.number();
		String cvvString = String.valueOf(cvv);
		assertThat(cvvString.length(), lessThanOrEqualTo(4));
	}



}
