package japrc2017.UI;

/*
 * ExamNumber : Y3851551
 */

import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import japrc2017.util.SimulationUtil;

public class AirportComponent extends JPanel{
	
	private JLabel jLabelImage;
	private JLabel jLabelDetail;
	private String imagePath;
	public AirportComponent(String detail, String imagePath) {
		this.imagePath = imagePath;
		jLabelImage = new JLabel("");
		jLabelDetail = new JLabel(detail);
		
		jLabelImage.setBounds(2,15,10,10);
		this.setImagePath(imagePath);
		this.setLayout(null);
		jLabelDetail.setFont(new Font("Dialog",0,8));
		jLabelDetail.setBounds(2,0, 60, 15);
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
