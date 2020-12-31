package com.cmsz.paas.web.user.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.web.base.action.AbstractAction;

@UnLogging
public class CreateImageAction extends AbstractAction{
	
	private ByteArrayInputStream bais;
	
	private static int WIDTH = 60;
	private static int HEIGHT = 20;
	public ByteArrayInputStream getBais() {
		return bais;
	}
	public void setBais(ByteArrayInputStream bais) {
		this.bais = bais;
	}
	
	
	private static String createRandom(){
		//验证码所有字符不包含 0、1、i、o、I、O、l  容易混淆输错
		String str = "23456789qwertyupasdfghjkzxcvbnmQWERTYUPASDFGHJKLZXCVBNM";
		Random r = new Random();
		char[] rands = new char[4]; 
		for (int i = 0; i < 4; i++) {
			rands[i] = str.charAt(r.nextInt(55));
		}
		return new String(rands);
	}
	
	/***
	 * 画背景
	 * @param g
	 */
	private void drawBackground(Graphics g){
		g.setColor(new Color(0xDCDCDC));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		for (int i = 0; i < 7; i++) {
			int x = (int) (Math.random() * WIDTH);
			int y = (int) (Math.random() * HEIGHT);
			int red = (int) (Math.random() * 255);
			int green = (int) (Math.random() * 255);
			int blue = (int) (Math.random() * 255);
			g.setColor(new Color(red,green,blue));
			g.drawOval(x, y, WIDTH, HEIGHT);
		}
	
/*		//背景颜色  
	    Color bkColor = Color.WHITE;  
	    //验证码的颜色  
	    Color[] catchaColor = {Color.MAGENTA, Color.BLACK, Color.BLUE, Color.CYAN, Color.GREEN, Color.ORANGE, Color.PINK};  
	      
	    //填充底色为灰白  
	    g.setColor(bkColor);  
	    g.fillRect(0, 0, WIDTH, HEIGHT);  */
	      
	    //画边框  
	    g.setColor(Color.BLACK);  
	    g.drawRect(0, 0, WIDTH-1, HEIGHT-1);  	
	}

	private void drawRands(Graphics g,String rands){
		g.setColor(Color.BLACK);
		g.setFont(new Font(null, Font.ITALIC | Font.BOLD, 18));  
		
		// 在不同的高度上输出验证码的每个字符
		g.drawString("" + rands.charAt(0), 1, 17);  
		g.drawString("" + rands.charAt(1), 16, 15);  
		g.drawString("" + rands.charAt(2), 31, 18); 
		g.drawString("" + rands.charAt(3), 46, 16);
	}
	
	public String getImage(){

		HttpServletResponse response = ServletActionContext.getResponse();
		// 设置浏览器不要缓存此图片  
        response.setHeader("Pragma", "no-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        
        response.setDateHeader("Expires", 0);  

        String rands = createRandom();  

        BufferedImage image = new BufferedImage(WIDTH, HEIGHT,  
                BufferedImage.TYPE_INT_RGB);  

        Graphics g = image.getGraphics();  

        // 产生图像  
        drawBackground(g);  

        drawRands(g, rands);  

        // 结束图像 的绘制 过程， 完成图像  
        g.dispose();  

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();  

        try {
			ImageIO.write(image, "jpeg", outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}  

        ByteArrayInputStream input = new ByteArrayInputStream(outputStream  
                .toByteArray());  

        this.setBais(input); 

        HttpSession session = ServletActionContext.getRequest().getSession();  

        session.setAttribute("checkCode", rands);  
          
        try {
			input.close();
			outputStream.close();  
		} catch (IOException e) {
			e.printStackTrace();
		}  
          
		
		return SUCCESS;
		
	}
}
