import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.analysis.*; 
import ddf.minim.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Sanlizer extends PApplet {




int BUFSIZE = 256;

Minim minim;

AudioInput in; //オーディオ入力
FFT fft; //FFTクラス
public void settings(){
  fullScreen();
}

public void setup() {
  
  
  //Minim初期化
  minim = new Minim(this);
  //ステレオでオーディオ入力
  in = minim.getLineIn(Minim.STEREO, BUFSIZE);
  //FFTを新規に行う
  fft = new FFT(in.bufferSize(), in.sampleRate());
  //分析窓は、ハミング窓で
  fft.window(FFT.HAMMING);
  
  colorMode(HSB, 360, 100, 100, 100);
  noStroke();
}

public void draw() {
  background(0);
  
  //FFT変換実行
  fft.forward(in.mix);

  
  for (int i = 0; i < fft.specSize(); i++) {
    
    //上下グラデーショングラフ生成
    float h = map(i, 0, fft.specSize(), 0, 360);
    stroke(h, 100, 100);
    strokeWeight(width/170);
    float x = map(i, -1, fft.specSize()*1, 1, width);
    line(x, height/2, x, height/2 - fft.getBand(i) * 1000);
    line(x, height/2, x, height/2 - fft.getBand(i) * -1000);
    
    //中心黒丸生成
    strokeWeight(width/190);
    line(x, height/2, x, height/2 - fft.getBand(i)* 0);
    
    //上下クログラフ生成
    stroke(0,0,0);
    strokeWeight(width/250);
    line(x, height/2, x, height/2 - fft.getBand(i)* 10);
    line(x, height/2, x, height/2 - fft.getBand(i)* -10);
  } 
  //fft.forward(in.mix);
}

public void stop() {
  minim.stop();
  super.stop();
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#FFFFFF", "--stop-color=#FF0505", "Sanlizer" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
