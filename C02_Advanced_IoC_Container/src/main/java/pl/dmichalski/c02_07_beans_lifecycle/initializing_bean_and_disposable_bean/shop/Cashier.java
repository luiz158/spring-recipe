package pl.dmichalski.c02_07_beans_lifecycle.initializing_bean_and_disposable_bean.shop;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.io.*;
import java.util.Date;

/**
 * Author: Daniel
 */
public class Cashier implements InitializingBean, DisposableBean {

    private String name;

    private String path;

    private BufferedWriter writer;

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void openFile() throws IOException {
        System.out.println("Init method called");
        File logFile = new File(path, name + ".txt");
        writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(logFile, true)));
    }

    public void checkout(ShoppingCart shoppingCart) throws IOException {
        double total = 0;
        for (Product product : shoppingCart.getItems()) {
            total += product.getPrice();
        }
        writer.write(new Date() + "\t" + total + "\r\n");
        writer.flush();
    }

    public void closeFile() throws IOException {
        System.out.println("Destroy method called");
        writer.close();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        openFile();
    }

    @Override
    public void destroy() throws Exception {
        closeFile();
    }
}
