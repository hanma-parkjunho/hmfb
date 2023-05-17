import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.yaml.snakeyaml.Yaml;

public class JasyptEnc {

	
	public static void main(String[] args) {
		
		if(args.length == 0 || args[0] == null) {
			System.out.println("매개변수가 올바르지 않습니다.");
			System.out.println("args[0] : 암호화키");
			return;
		}
		
		if(args.length == 1 || args[1] == null) {
			System.out.println("매개변수가 올바르지 않습니다.");
			System.out.println("args[1] : 외부 yml 설정 파일 경로");
			return;
		}
		
		try {
			
			PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		    SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		    config.setPassword(args[0]);
		    config.setAlgorithm("PBEWithMD5AndDES");
		    config.setPoolSize("1");
		    encryptor.setConfig(config);
		        
			Iterable<?> iterable = new Yaml().loadAll(new FileReader(args[1]));
			
			Iterator<?> iterator = iterable.iterator();
			
			while(iterator.hasNext()) {
				Map<?, ?> map = (Map<?, ?>) iterator.next();
				recursYml(map, encryptor);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private static void recursYml(Map<?, ?> map, PooledPBEStringEncryptor encryptor) {
		
		for( Object key : map.keySet() ){
			Object v = map.get(key);
			if(v instanceof Map<?, ?>) {
				recursYml(((Map<?, ?>) v), encryptor);
			} else {
				String strVal = v.toString();
				if(strVal.startsWith("ENC(")) {
					strVal = strVal.substring("ENC(".length(), strVal.indexOf(")"));
					System.out.println(key + ": DEC(" + encryptor.decrypt(strVal)+")");
				} else if(strVal.startsWith("DEC(")) {
					strVal = strVal.substring("DEC(".length(), strVal.indexOf(")"));
					System.out.println(key + ": ENC(" + encryptor.encrypt(strVal)+")");
				} 
			}
        }
		
	}

}


