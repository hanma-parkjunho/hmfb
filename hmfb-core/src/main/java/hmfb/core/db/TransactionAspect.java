package hmfb.core.db;

import java.util.Collections;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**

 * @FileName : TransactionAspect.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : Transaction Aspect Config 클레스

 * @변경이력 :

 */

@Configuration
public class TransactionAspect {

	private static final String AOP_TRANSACTION_METHOD_NAME="*";
    private static final String AOP_TRANSACTION_EXPRESSION="execution(* hmfb..*Impl.*(..))";
	
    @Autowired
    private TransactionManager transactionManager;

    /**
	
	  * @Method Name : transactionAdvice
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : TransactionInterceptor 생성 
	
	  * @변경이력 : 
	
	  */
    @Bean
    public TransactionInterceptor transactionAdvice(){
        MatchAlwaysTransactionAttributeSource source = new MatchAlwaysTransactionAttributeSource();
        RuleBasedTransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute();
        transactionAttribute.setName(AOP_TRANSACTION_METHOD_NAME);
        transactionAttribute.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        source.setTransactionAttribute(transactionAttribute);

        return new TransactionInterceptor(transactionManager, source);
    }

    /**
	
	  * @Method Name : transactionAdviceAdvisor
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : DefaultPointcutAdvisor 생성 
	
	  * @변경이력 : 
	
	  */
    @Bean
    public Advisor transactionAdviceAdvisor(){
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_TRANSACTION_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, transactionAdvice());
    }
    
}
