package ch.hszt.kfh.rockstocks.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import ch.hszt.kfh.rockstocks.Player;
import ch.hszt.kfh.rockstocks.drains.AudioLineDrain;
import ch.hszt.kfh.rockstocks.modulators.IModulator;
import ch.hszt.kfh.rockstocks.modulators.PitchShiftModulator;
import ch.hszt.kfh.rockstocks.series.TimeSeriesProviderRegistry;
import ch.hszt.kfh.rockstocks.series.historical.HistoricalSmiSimulationSeriesProvider;
import ch.hszt.kfh.rockstocks.sources.AudioFileSource;

public class FileDialog extends javax.swing.JDialog {
	
	/**
	 * Auto-generated main method to display this FileDialog
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frame = new JFrame("Rockstocks");
				FileDialog inst = new FileDialog(frame);
				inst.setVisible(true);
			}
		});
	}

	{
		// Set Look & Feel
		try {
			javax.swing.UIManager
			.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	private static JFrame frame;

	private JMenuBar menuBar;
	private JMenu menuHelp;
	private JMenuItem menuItemAbout;

	private JLabel fileLabel;
	private JTextField musicFile;
	private JButton browseButton;
	private JFileChooser fileChooser;

	private JButton playButton;
	private JButton stopButton;
	private JButton pauseButton;

	private JLabel modulationLabel;
	private JLabel pitchLabel;
	private JComboBox pitchDataRow;
	private JComboBox pitchData;
	private JCheckBox pitchAktiviert;

	private JLabel tempoLabel;
	private JComboBox tempoDataRow;
	private JComboBox tempoData;
	private JCheckBox tempoAktiviert;

	private JLabel volumeLabel;
	private JComboBox volumeDataRow;
	private JComboBox volumeData;
	private JCheckBox volumeAktiviert;

	private JLabel tonartLabel;
	private JComboBox tonartComboBox;
	private JCheckBox tonartAktiviert;


	public FileDialog(JFrame frame) {
		super(frame);
		initGUI();
	}

	Player player = new Player();
	
	IModulator mod = new PitchShiftModulator();
	
	private void initGUI() {
		try {
			player.setDrain(new AudioLineDrain());

			mod.setTimeSeries(new HistoricalSmiSimulationSeriesProvider().getPriceSeriesForIsin("CH0012255151"));
			player.getModulators().add(mod);

			TimeSeriesProviderRegistry registry = new TimeSeriesProviderRegistry();
			getContentPane().setLayout(null);
			{
				menuBar = new JMenuBar();
				setJMenuBar(menuBar);
				{
					menuHelp = new JMenu();
					menuBar.add(menuHelp);
					menuHelp.setText("Hilfe");
					{
						menuItemAbout = new JMenuItem();
						menuHelp.add(menuItemAbout);
						menuItemAbout.setText("�ber");
					}
				}
			}
			{
				fileLabel = new JLabel("Auswahl der zu modulierenden Audiodatei:");
				getContentPane().add(fileLabel);
				fileLabel.setBounds(16, 5, 242, 16);
			}
			{
				musicFile = new JTextField();
				getContentPane().add(musicFile);
				musicFile.setBounds(16, 24, 125, 21);
			}
			{	fileChooser = new JFileChooser();
			}

			{
				browseButton = new JButton();
				browseButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						int retVal = fileChooser.showOpenDialog(frame);
						if (retVal == JFileChooser.APPROVE_OPTION){
							musicFile.setText(fileChooser.getSelectedFile().getAbsolutePath());
							player.setSource(new AudioFileSource(fileChooser.getSelectedFile()));
						}
					}
				});
				getContentPane().add(browseButton);
				browseButton.setText("Browse");
				browseButton.setBounds(154, 24, 87, 21);
			}

				{
					playButton = new JButton();
					getContentPane().add(playButton);
					playButton.setIcon(new ImageIcon("src/main/resources/gui/play-icon.png"));
					playButton.setBounds(16, 50, 60, 44);
					playButton.addActionListener(new ActionListener()  {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							
							new Thread(new Runnable() {

								@Override
								public void run() {
									player.play();
								}
								
							}).start();
							
						}
					});
				}
				{
					stopButton = new JButton();
					getContentPane().add(stopButton);
					stopButton.setIcon(new ImageIcon("src/main/resources/gui/stop-icon.png"));
					stopButton.setBounds(90, 50, 60, 44);
				}
				{
					pauseButton = new JButton();
					getContentPane().add(pauseButton);
					pauseButton.setIcon(new ImageIcon("src/main/resources/gui/pause-icon.png"));
					pauseButton.setBounds(164, 50, 60, 44);
					pauseButton.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							player.pause();
						}
					});
				}
				{
					modulationLabel = new JLabel("Modulation:");
					getContentPane().add(modulationLabel);
					modulationLabel.setBounds(16, 106, 75, 16);
				}
				{
					pitchLabel = new JLabel("Pitch");
					getContentPane().add(pitchLabel);
					pitchLabel.setBounds(16, 124, 27, 16);
				}
				{
					tempoLabel = new JLabel("Tempo");
					getContentPane().add(tempoLabel);
					tempoLabel.setBounds(16, 152, 39, 16);
				}
				{
					volumeLabel = new JLabel("Volumen");
					getContentPane().add(volumeLabel);
					volumeLabel.setBounds(16, 180, 50, 16);
				}
				{
					tonartLabel = new JLabel("Tonart");
					getContentPane().add(tonartLabel);
					tonartLabel.setBounds(16, 208, 79, 16);
				}
				{
//					ComboBoxModel pitchDataRowModel = new DefaultComboBoxModel(
//							new String[] { "Item One", "Item Two" });
					pitchDataRow = new JComboBox();
					getContentPane().add(pitchDataRow);
					pitchDataRow.setModel(registry.getModel());
					pitchDataRow.setBounds(90, 119, 83, 26);
				}
				{
					ComboBoxModel tempoDataRowModel = new DefaultComboBoxModel(
							new String[] { "Item One", "Item Two" });
					tempoDataRow = new JComboBox();
					getContentPane().add(tempoDataRow);
					tempoDataRow.setModel(tempoDataRowModel);
					tempoDataRow.setBounds(90, 147, 83, 26);
				}
				{
					ComboBoxModel volumeDataRowModel = new DefaultComboBoxModel(
							new String[] { "Item One", "Item Two" });
					volumeDataRow = new JComboBox();
					getContentPane().add(volumeDataRow);
					volumeDataRow.setModel(volumeDataRowModel);
					volumeDataRow.setBounds(90, 175, 83, 26);
				}
				{
					ComboBoxModel tonartComboBoxModel = new DefaultComboBoxModel(
							new String[] { "Ionisch", "Dorisch", "Phrygisch",
									"Lydisch", "Mixolydisch", "�olisch", "Lokrisch" });
					tonartComboBox = new JComboBox();
					getContentPane().add(tonartComboBox);
					tonartComboBox.setModel(tonartComboBoxModel);
					tonartComboBox.setBounds(90, 203, 83, 26);
				}
				{
					ComboBoxModel pitchDataModel = new DefaultComboBoxModel(
							new String[] { "Item One", "Item Two" });
					pitchData = new JComboBox();
					getContentPane().add(pitchData);
					pitchData.setModel(pitchDataModel);
					pitchData.setBounds(198, 119, 83, 26);
				}
				{
					ComboBoxModel tempoDataModel = new DefaultComboBoxModel(
							new String[] { "Item One", "Item Two" });
					tempoData = new JComboBox();
					getContentPane().add(tempoData);
					tempoData.setModel(tempoDataModel);
					tempoData.setBounds(198, 147, 83, 26);
				}
				{
					ComboBoxModel volumeDataModel = new DefaultComboBoxModel(
							new String[] { "Item One", "Item Two" });
					volumeData = new JComboBox();
					getContentPane().add(volumeData);
					volumeData.setModel(volumeDataModel);
					volumeData.setBounds(198, 175, 83, 26);
				}
				{
					pitchAktiviert = new JCheckBox("aktiviert");
					getContentPane().add(pitchAktiviert);
					pitchAktiviert.setBounds(306, 123, 63, 18);
				}
				{
					tempoAktiviert = new JCheckBox("aktiviert");
					getContentPane().add(tempoAktiviert);
					tempoAktiviert.setBounds(306, 151, 63, 18);
				}
				{
					volumeAktiviert = new JCheckBox();
					getContentPane().add(volumeAktiviert);
					volumeAktiviert.setText("aktiviert");
					volumeAktiviert.setBounds(306, 179, 63, 18);
				}
				{
					tonartAktiviert = new JCheckBox("aktiviert");
					getContentPane().add(tonartAktiviert);
					tonartAktiviert.setBounds(306, 207, 63, 18);
				}
				setSize(400, 300);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	public static JFrame getFrame() {
		return frame;
	}

	public static void setFrame(JFrame frame) {
		FileDialog.frame = frame;
	}

	public JMenuBar getMenuBar() {
		return menuBar;
	}

	public void setMenuBar(JMenuBar menuBar) {
		this.menuBar = menuBar;
	}

	public JMenu getMenuHelp() {
		return menuHelp;
	}

	public void setMenuHelp(JMenu menuHelp) {
		this.menuHelp = menuHelp;
	}

	public JMenuItem getMenuItemAbout() {
		return menuItemAbout;
	}

	public void setMenuItemAbout(JMenuItem menuItemAbout) {
		this.menuItemAbout = menuItemAbout;
	}

	public JLabel getFileLabel() {
		return fileLabel;
	}

	public void setFileLabel(JLabel fileLabel) {
		this.fileLabel = fileLabel;
	}

	public JTextField getMusicFile() {
		return musicFile;
	}

	public void setMusicFile(JTextField musicFile) {
		this.musicFile = musicFile;
	}

	public JButton getBrowseButton() {
		return browseButton;
	}

	public void setBrowseButton(JButton browseButton) {
		this.browseButton = browseButton;
	}

	public JFileChooser getFileChooser() {
		return fileChooser;
	}

	public void setFileChooser(JFileChooser fileChooser) {
		this.fileChooser = fileChooser;
	}

	public JButton getPlayButton() {
		return playButton;
	}

	public void setPlayButton(JButton playButton) {
		this.playButton = playButton;
	}

	public JButton getStopButton() {
		return stopButton;
	}

	public void setStopButton(JButton stopButton) {
		this.stopButton = stopButton;
	}

	public JButton getPauseButton() {
		return pauseButton;
	}

	public void setPauseButton(JButton pauseButton) {
		this.pauseButton = pauseButton;
	}

	public JLabel getModulationLabel() {
		return modulationLabel;
	}

	public void setModulationLabel(JLabel modulationLabel) {
		this.modulationLabel = modulationLabel;
	}

	public JLabel getPitchLabel() {
		return pitchLabel;
	}

	public void setPitchLabel(JLabel pitchLabel) {
		this.pitchLabel = pitchLabel;
	}

	public JComboBox getPitchDataRow() {
		return pitchDataRow;
	}

	public void setPitchDataRow(JComboBox pitchDataRow) {
		this.pitchDataRow = pitchDataRow;
	}

	public JComboBox getPitchData() {
		return pitchData;
	}

	public void setPitchData(JComboBox pitchData) {
		this.pitchData = pitchData;
	}

	public JCheckBox getPitchAktiviert() {
		return pitchAktiviert;
	}

	public void setPitchAktiviert(JCheckBox pitchAktiviert) {
		this.pitchAktiviert = pitchAktiviert;
	}

	public JLabel getTempoLabel() {
		return tempoLabel;
	}

	public void setTempoLabel(JLabel tempoLabel) {
		this.tempoLabel = tempoLabel;
	}

	public JComboBox getTempoDataRow() {
		return tempoDataRow;
	}

	public void setTempoDataRow(JComboBox tempoDataRow) {
		this.tempoDataRow = tempoDataRow;
	}

	public JComboBox getTempoData() {
		return tempoData;
	}

	public void setTempoData(JComboBox tempoData) {
		this.tempoData = tempoData;
	}

	public JCheckBox getTempoAktiviert() {
		return tempoAktiviert;
	}

	public void setTempoAktiviert(JCheckBox tempoAktiviert) {
		this.tempoAktiviert = tempoAktiviert;
	}

	public JLabel getVolumeLabel() {
		return volumeLabel;
	}

	public void setVolumeLabel(JLabel volumeLabel) {
		this.volumeLabel = volumeLabel;
	}

	public JComboBox getVolumeDataRow() {
		return volumeDataRow;
	}

	public void setVolumeDataRow(JComboBox volumeDataRow) {
		this.volumeDataRow = volumeDataRow;
	}

	public JComboBox getVolumeData() {
		return volumeData;
	}

	public void setVolumeData(JComboBox volumeData) {
		this.volumeData = volumeData;
	}

	public JCheckBox getVolumeAktiviert() {
		return volumeAktiviert;
	}

	public void setVolumeAktiviert(JCheckBox volumeAktiviert) {
		this.volumeAktiviert = volumeAktiviert;
	}

	public JLabel getTonartLabel() {
		return tonartLabel;
	}

	public void setTonartLabel(JLabel tonartLabel) {
		this.tonartLabel = tonartLabel;
	}

	public JComboBox getTonartComboBox() {
		return tonartComboBox;
	}

	public void setTonartComboBox(JComboBox tonartComboBox) {
		this.tonartComboBox = tonartComboBox;
	}

	public JCheckBox getTonartAktiviert() {
		return tonartAktiviert;
	}

	public void setTonartAktiviert(JCheckBox tonartAktiviert) {
		this.tonartAktiviert = tonartAktiviert;
	}

	}
