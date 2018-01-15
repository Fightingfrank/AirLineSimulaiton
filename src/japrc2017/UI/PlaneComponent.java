package japrc2017.UI;

/*
 * ExamNumber : Y3851551
 */

import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import japrc2017.util.SimulationUtil;

public class PlaneComponent extends JPanel{
		
	public static void main(String args[]) {
		JFrame jframe = new JFrame();
		
		PlaneComponent pcom = new PlaneComponent("LHR_001", "red_plane.jpg");
		
		pcom.setBounds(0, 0, 45, 37);
		
		pcom.setBorder(new LineBorder(new java.awt.Color(0,0,0), 1, false));
		jframe.setLayout(null);
		jframe.setSize(100, 100);
		jframe.add(pcom);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
	}
	
	
	private JLabel jLabelImage;
	private JLabel jLabelDetail;
	private String imagePath;
	public PlaneComponent(String detail, String Path) {
		
		this.imagePath = Path;
		jLabelImage = new JLabel("");
		jLabelDetail = new JLabel(detail);
		
		jLabelImage.setBounds(10,15,15,20);
		this.setImagePath(imagePath);
		this.setLayout(null);
		jLabelDetail.setFont(new Font("Dialog",0,10));
		jLabelDetail.setBounds(0,0, 60, 15);
		
		this.add(jLabelImage);
		this.add(jLabelDetail);
		this.setBackground(null);
		this.setOpaque(false);
	}
	
	public void setDetailVisiable(boolean t) {
		jLabelDetail.setVisible(t);
	}
	public void setImagePath(String path) {
		this.imagePath = path;
		ImageIcon image = new ImageIcon(SimulationUtil.getFilePath(imagePath));
		image.setImage(image.getImage().getScaledInstance(jLabelImage.getWidth(),jLabelImage.getHeight(), Image.SCALE_AREA_AVERAGING));
		jLabelImage.setIcon(image);
	}
	
	public String getImagePath() {
		return this.imagePath;
	}
	
	public String getDetail() {
		return jLabelDetail.getText();
	}
}
