package com.simulation.notebook.advisor.param;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.stereotype.Component;

/**
 * ParamBarrier注解Advisor
 * @author Administrator

 */
@Component
public class ParamBarrierAdvisor implements PointcutAdvisor {

    @Override
    public Pointcut getPointcut() {
        return new ParamBarrierPointCut();
    }

    @Override
    public Advice getAdvice() {
        return new ParamBarrierAdvice();
    }

    @Override
    public boolean isPerInstance() {
        return false;
    }
}
