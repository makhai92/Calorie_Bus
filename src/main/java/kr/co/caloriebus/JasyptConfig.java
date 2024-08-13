package kr.co.caloriebus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;


@Configuration
public class JasyptConfig {

	@Primary
	@Bean(name = "jasyptStringEncryptor")
	public StringEncryptor stringEncryptor() {
		String password = "Salppaejo";
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();

		SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		config.setPassword(password); // 암호화할 때 사용하는 키
		config.setAlgorithm("PBEWithSHA256And256BitAES-CBC-BC"); // 암호화 알고리즘
		config.setKeyObtentionIterations("1000"); // 반복할 해싱 회수
		config.setPoolSize("1"); // 인스턴스 pool
		config.setProvider(new BouncyCastleProvider());
		config.setSaltGeneratorClassName("org.jasypt.salt.ZeroSaltGenerator"); // salt 생성 클래스
		config.setStringOutputType("base64"); // 인코딩 방식

		encryptor.setConfig(config);
		return encryptor;
	}
}