package gbu.ui.foundation.base;

import gbu.ui.foundation.frame.ToftPanel;
import gbu.ui.sm.desktop.DeskTop;

import java.lang.reflect.Method;
import java.util.Map;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;

public class ToftPaneProxy {
    private Class paneClass;
    private String funCode;
    private Enhancer  enhancer;
    public ToftPaneProxy(Class paneClass,String funCode){
        this.paneClass = paneClass;
        this.funCode = funCode;
        enhancer  = new Enhancer();
        enhancer.setInterceptDuringConstruction(true);
        enhancer.setSuperclass(paneClass);   
        enhancer.setCallbacks(new Callback[]{NoOp.INSTANCE,new ThisProxy()});  
        enhancer.setCallbackFilter(new ThisCallbckFilter());
    }
    
    public ToftPanel getToftPanel(){
        ToftPanel toftPanel = (ToftPanel) enhancer.create();
        return toftPanel;
    }
    
    private class ThisProxy implements MethodInterceptor {   
        public Object intercept(Object arg0, Method arg1, Object[] arg2,   
                MethodProxy arg3) throws Throwable {   
            ToftPanel panel = (ToftPanel) arg0;
            if(panel.getNodeMap()==null){
                panel.setNodeMap((Map) DeskTop.getNodeMap().get(funCode));
            }
            return arg3.invokeSuper(arg0, arg2);   
        }
  
    }  
    
    private class ThisCallbckFilter implements CallbackFilter{

        public int accept(Method method) {
            if(method.getName().equals("getBillDefName")){
                return 1;
            }
            return 0;
        }
        
    }

}
