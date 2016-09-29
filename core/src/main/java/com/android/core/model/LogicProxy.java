package com.android.core.model;


import com.android.core.base.BasePresenter;
import com.android.core.base.BaseView;
import com.android.core.model.annotation.Implement;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author: 蜡笔小新
 * @date: 2016-05-31 14:15
 * @GitHub: https://github.com/meikoz
 *
 * edit change by oreo at 20160822
 * https://github.com/oreo
 */
public class LogicProxy {
    private static final LogicProxy m_instance = new LogicProxy();

    public static LogicProxy getInstance() {
        return m_instance;
    }

    private LogicProxy() {
        m_objects = new HashMap<>();
    }

    private Map<Class, Object> m_objects;

    public void init(Class... clss) {
        List<Class> list = new LinkedList<Class>();
        for (Class cls : clss) {
            if (cls.isAnnotationPresent(Implement.class)) {
                list.add(cls);
                for (Annotation ann : cls.getDeclaredAnnotations()) {
                    if (ann instanceof Implement) {
                        try {
                            m_objects.put(cls, ((Implement) ann).value().newInstance());
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public <T> T getProxy(Class cls) {
        return (T) m_objects.get(cls);
    }

    /**
     *
     *
     * @param cls logic(presenter)'s interface
     * @param o   view(activity  ||  fragment)
     * @param <T> (logic)presenter's Impl
     * @return
     */
    public <T> T bind(Class cls, BaseView o) {

        if(!m_objects.containsKey(cls)){
            init(cls);
        }
        else{
            BasePresenter presenter=((BasePresenter)m_objects.get(cls));
            if(o!=presenter.getView()){
                if(presenter.getView()==null)presenter.attachView(o);
                else{
                    init(cls);
                }
            }
        }
        BasePresenter presenter=((BasePresenter)m_objects.get(cls));
        return (T) presenter;

       /* Object ret = m_objects.get(cls);
        ((BasePresenter) ret).attachView(o);
        return (T) ret;*/
    }

}
