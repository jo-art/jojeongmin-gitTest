import java.awt.*;
import java.awt.event.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
// borer를 사용하기 위해서 import 시킴
import javax.swing.border.*;

//이게임은 99초안에 모든 카드를 뒤집어서 짝을 마추는 게임입니다.
//hint를 눌르면 카드를 뒤집어서 볼수있습니다. 단 hint를 누르면 -20점을 당하게 됩니다.  
//새로 시작을누르게되면 난이도에 따라 easy, normal, hard등의 
//4x4, 4x5, 4x6등의퍼즐 크기를 선택할 수 있습니다.
//한번 맞추는것이 실패할때마다 -5점을 당하게 됩니다. 

class FindGame extends JFrame implements ActionListener {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
// static final 상수
  private static final String SCORE = "점";
  private static final String REMAIN = "개";
  private static final String TIMER = "초";
  private static final String VALUE = "0";

  // JLabel
  // 변화는 값과 고정된 값이 필요
  private JLabel jlblScore_Value;
  private JLabel jlblRemain_Value;
  private JLabel jlblTimer_Value;
  private JLabel jlblScore;
  private JLabel jlblRemain;
  private JLabel jlblTimer;
  private JLabel jname;

  TimeThread thread;

  private JButton hint;
  private JButton newgame;

  // JToggleButton
  private JButton[] coverBtns;
  // private JButton[] showBtns;
  public int btn_Item;

  // GridLayout
  private int row;
  private int col;

  // 버튼의 상태를 위한

  private int[] btnstate;
  GameBtns btnpatten;
  private String[] RanOutput;
  int temp1 = -1, temp2 = -1;
  int nowremain;
  int nowscore;
  static int scores = 0;
	private JPanel jpnlCenter;
	private Clip clip;
  // 생성자
  FindGame() {
    super("짝 맞추기 퍼즐 게임");
    this.init();
    this.setLayout();
    this.setBtns(true);
    this.addListener();
    this.showJFrame();
		//this.initGameOverPane();
	}

  FindGame(JDialog owner, int row, int col) {
    super("짝 맞추기 퍼즐 게임");
    this.row = row;
    this.col = col;
    this.init();
    this.setLayout();
    //this.setBackground(Color.red);

    this.setBtns(this.row, this.col, true);
    this.setInit();
    this.addListener();
    this.showJFrame();
    this.initgame();
    //this.initGameOverPane();
  }

	// 초기화
  private void init() {
    this.nowremain = 0;
    this.nowscore = 0;
    this.btnstate = new int[100];
    for (int i = 0; i < 100; i++) {
      this.btnstate[i] = 0;
    }
    this.btnpatten = new GameBtns();

    // JLabel 초기화 각각의 상수값 사용
    this.jlblScore_Value = new JLabel(VALUE);
    this.jlblRemain_Value = new JLabel(VALUE);
    this.jlblTimer_Value = new JLabel(VALUE);

    this.jlblScore = new JLabel(SCORE);
    this.jlblRemain = new JLabel(REMAIN);
    this.jlblTimer = new JLabel(TIMER);
    this.jname = new JLabel("by 이수진, 정현아, 유가영");

    //시작 버튼과 새로 시작 버튼 이다.
    this.hint = new JButton("힌트 보기");
    this.newgame = new JButton("새로 시작");
    this.hint.addActionListener(this);
    this.newgame.addActionListener(this);

    hint.setBackground(Color.pink);
    newgame.setBackground(Color.pink);
    playBgm();
  }

  //배경 음악 재생
  private void playBgm() {
    try {
      AudioInputStream audioInputStream = AudioSystem
          .getAudioInputStream(new BufferedInputStream(new FileInputStream(new File("./sound/bgm.wav"))));
      clip = AudioSystem.getClip();
      clip.open(audioInputStream);
      clip.loop(100);
      clip.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // 버튼들과 레이아웃들의 버튼 배치 함수
  private void setLayout() {
    // 각 JPanel들을 통합하기 위해 최상위 JPanel인 jpnlEast의 FlowLayout을 GridLayout로 설정
    JPanel jpnlEast = new JPanel(new GridLayout(0, 1));
    JPanel jmenu = new JPanel(new FlowLayout());
    JPanel jpnlScore = new JPanel();
    JPanel jpnlRemain = new JPanel();
    JPanel jpnlTimer = new JPanel();
    JPanel jhint = new JPanel();
    JPanel jnewgame = new JPanel();
    JPanel jlabel = new JPanel();

    // 각각의 JPanel에 알맞게 해당하는 컨포넌트를 부착
    // setBorder()를 이용하여 각각 해당하는 TiltedBorder() 속성을 부여

    //점수와 남아 있는 카드의 갯수 타이머 힌드 그리고 마지막으로 새로 시작 버튼의 래이아웃 위치를
    //결정한다
    jlabel.add(jname);

    jpnlScore.add(jlblScore_Value);
    jpnlScore.add(jlblScore);
    jpnlScore.setBorder(new TitledBorder("★점수★"));

    jpnlRemain.add(jlblRemain_Value);
    jpnlRemain.add(jlblRemain);
    jpnlRemain.setBorder(new TitledBorder("★남은 퍼즐 갯수★"));

    jpnlTimer.add(jlblTimer_Value);
    jpnlTimer.add(jlblTimer);
    jpnlTimer.setBorder(new TitledBorder("★타이머★"));

    jhint.add(hint);
    jnewgame.add(newgame);
    // 통합을 위한 최상위 JPanel에 각각의 JPanel을 부착
    jpnlEast.add(jpnlScore);
    jpnlEast.add(jpnlRemain);
    jpnlEast.add(jpnlTimer);
    jmenu.add(jnewgame);
    jmenu.add(jhint);
    jmenu.add(jname);

    // 최상위 JPanel을 BorderLauout을 이용하여 EAST에 부착
    this.add(jmenu, BorderLayout.NORTH);
    this.add(jpnlEast, BorderLayout.SOUTH);

    jmenu.setBackground(new Color(255, 217, 236));
    jpnlScore.setBackground(new Color(255, 217, 236));
    jpnlRemain.setBackground(new Color(255, 217, 236));
    jpnlTimer.setBackground(new Color(255, 217, 236));
    jhint.setBackground(new Color(255, 217, 236));
    jnewgame.setBackground(new Color(255, 217, 236));

  }

  private void addListener() {
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent we) {
        // 종료버튼 클릭시 아무런 동작을 하지 않는다.
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // JOptionPane을 이용하여 showConfirmDialog를 보여주고 그 해당값을 choice에
        // 저장한다.
        int choice = JOptionPane.showConfirmDialog(FindGame.this,
            "게임을 끝내겠습니까?", "종료", JOptionPane.YES_NO_OPTION);

        if (choice == 0) {
          // 종료버튼을 클릭시 종료한다.
          setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
      }
    });

    // 버튼에 액션을 추가
    this.InsertAction();
  }

  //버튼들의
  private void showJFrame() {
    this.pack();
    this.setLocation(200, 200);
    this.setVisible(true);
  }

  public void actionPerformed(ActionEvent ae) {
    Object o = ae.getSource();
    if (o == this.hint) {
      hintgame();
      initgame();
      scores -= 20;
    } else if (o == this.newgame) {
      playCorrectSound();
      new StartDlg(this);
    }

    for (int i = 0; i < this.btn_Item; i++) {
      if (o.equals(this.coverBtns[i])) { // 임의의 버튼을 눌렀을 때는,
        doing(i);
      }
    }
  }

  private void playCorrectSound() {
    try {
      AudioInputStream audioInputStream = AudioSystem
          .getAudioInputStream(new BufferedInputStream(new FileInputStream(new File("./sound/correct.wav"))));
      Clip clip = AudioSystem.getClip();
      clip.open(audioInputStream);
      clip.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // 처음 게임을 시작하였을떄 이미지들을 세팅 하는 부분이다.
  //게임을 시작하기전의 상태로서 버튼들을 세팅한다
  @SuppressWarnings("unused")
private void setBtns(boolean flag) {
    this.row = 4;
    this.col = 4;
    this.btn_Item = this.row * this.col;

    this.coverBtns = new JButton[this.btn_Item];
    for (int index = 0; index < btn_Item; index++) {
      //처음 이미지들을 모두 별 이미지로 세팅한다
      this.coverBtns[index] = new JButton(
          new ImageIcon("./Image/que.PNG"));
      this.coverBtns[index].setEnabled(false);
      this.coverBtns[index].setBackground(Color.pink);//////////버튼색바꿈(첫화면)
    }
    jpnlCenter = new JPanel(new GridLayout(this.row, this.col, 10,
        10));
    int index = 0;
		for (JButton temp : this.coverBtns) {
			jpnlCenter.add(temp);
			index++;
		}
		this.add(jpnlCenter, BorderLayout.CENTER);
    jpnlCenter.setBackground(new Color(255, 217, 236));
  }

  //게임 오버시 화면 초기화
	private void initGameOverPane() {
		jpnlCenter.removeAll();
		Label label = new Label("Game Over", 1);
		label.setSize(this.row, this.col);
		label.setFont(new Font("Serif", Font.BOLD, 30));
		jpnlCenter.add(label);
		this.add(jpnlCenter, BorderLayout.CENTER);
		this.revalidate();
	}


  //게임 시작 할때 상태 초기화
  public void initbtnstate(int size) {
    for (int i = 0; i < size; size++) {
      this.btnstate[i] = 0;
    }
  }

  // 버튼 하나를 클릭할경우 버튼의 id 넘버와 그id에 맞는 이미지를 넣어준다
  private void showsign(JButton btn, String str, int i) {
    btn.setIcon(new ImageIcon(str));
    this.btnstate[i] = 1;
  }

  //버튼이 눌린다음에 그 상태를 인에이블로 바꿔서 같은것들이 클릭되지 않게 한다
  private void showgreen(JButton btn) {
    btn.setEnabled(false);
  }

  //처음 이미지들을 보여주고 그 이미지를 별 표시로 하여 카드를 덟는다
  private void initgame() {
    imageThread thread = new imageThread(coverBtns, "./Image/que.png");
    thread.setDaemon(true);
    thread.start();
  }

  //힌트를 구현한 함수로 이미지가 들어있는 배열과 이미지 버튼들에 맞게 이미지를 다시 한번
  //보여주게 된다.
  private void hintgame() {
    imagehint thread = new imagehint(coverBtns, RanOutput);
    thread.setDaemon(true);
    thread.start();
  }

  //버튼 하나의 동작, 원래대로 돌아가자.
  private void showorigin(JButton btn) {
    btn.setIcon(new ImageIcon("./Image/que.png"));
    btn.setBackground(Color.black);
  }

  //현재 눌려진 버튼들의 갯수를 센다
  private int howmany(int size) {
    int howmany = 0;
    for (int i = 0; i < size; i++) {
			if (this.btnstate[i] == 1) {
				howmany++;
			}
    }
    return howmany;
  }

  // 두개 뒤집혔을 때 선택된 두 버튼의 위치 정보를 받아서 그 버튼을 비교하여  같은지
  //다른지를 처리하는 함수로써 같을경우 score를 증가 시켜 점수를 증가 시키고
  //시간값을 얻어와서 그 시간을 점수로 환산해서 준다
  private void ispair(int A, int B) {

    String btn1 = this.RanOutput[A];
    String btn2 = this.RanOutput[B];

    //버튼의 id값이 같은경우 처리
    if (btn1.equals(btn2)) {
    	playCorrectSound();
      int remain = (this.btn_Item / 2) - this.nowremain - 1;////////////////////////////////남은개수고침

      if (remain == 0) {//////////
        scores += timer(Integer.parseInt(this.jlblTimer_Value.getText()));
      }

      scores += 20;
      this.nowremain++;
      this.jlblRemain_Value.setText(String.valueOf(remain));
      this.jlblScore_Value.setText(String.valueOf(FindGame.scores));
      this.showgreen(this.coverBtns[A]);
      this.showgreen(this.coverBtns[B]);
      this.btnstate[A] = 2;
      this.btnstate[B] = 2;

    } else {
      //버튼의 id값이 다를경우 처리
      scores -= 5;
			thread.inCorrect();

      this.jlblScore_Value.setText(String.valueOf(FindGame.scores));
      this.nowscore = 5;
      this.btnstate[A] = 1;
      this.btnstate[B] = 1;
    }
  }

  public void getpatten(int size) {
    // 이미지 버튼을 랜덤으로 생성하여 이미지 가져온다
    this.RanOutput = this.btnpatten.RanArray(size);
  }

  private void doing(int i) {
    // 버튼 하나를 눌렀을 떄 수행되는 동작들. i는 배열 위치.
    this.showsign(this.coverBtns[i], this.RanOutput[i], i);
    if (howmany(this.btn_Item) == 2) {
      // 지까지 두개가 뒤집혀여 있으면,
      for (int a = 0; a < this.btn_Item; a++) {
        // 두개의 위치 정보를 얻어서,
        if (this.btnstate[a] == 1 && a != i) {
          this.temp1 = a;
        }
      }

      this.temp2 = i;
      this.ispair(temp1, temp2); // 같으면 뒤집고, 다르면 ? 이미지를 넣는다
    }

    if (howmany(this.btn_Item) == 3) {
      // 뒤집혀진게 3개이면 두개를 뒤집고 하나는 뒤집지 않고 남긴다
      for (int a = 0; a < this.btn_Item && a != i; a++) {
				if (this.btnstate[a] == 1 && temp1 == -1) {
					this.temp1 = a;
				}
      }

      for (int a = 0; a < this.btn_Item && a != i && a != this.temp1; a++) {
				if (this.btnstate[a] == 1) {
					this.temp2 = a;
				}
      }

      // 둘은 다시 뒤집고,
      showorigin(this.coverBtns[temp1]);
      showorigin(this.coverBtns[temp2]);
      this.btnstate[temp1] = 0;
      this.btnstate[temp2] = 0;
    }

  }

  //시간값에 따른 점수를 더해준다
  private int timer(int i) {
    if (100 >= i && 90 <= i) {
      return 10;
    } else if (100 >= i && 90 <= i) {
      return 20;
    } else if (89 >= i && 80 <= i) {
      return 30;
    } else if (79 >= i && 70 <= i) {
      return 40;
    } else if (69 >= i && 60 <= i) {
      return 50;
    } else if (59 >= i && 50 <= i) {
      return 60;
    } else if (49 >= i && 40 <= i) {
      return 70;
    } else if (39 >= i && 30 <= i) {
      return 80;
    } else if (29 >= i && 20 <= i) {
      return 90;
    } else if (19 >= i && 10 <= i) {
      return 100;
    } else if (9 >= i && 0 <= i) {
      return 110;
    }
    return i;

  }

  public void setBtns(int row, int col, boolean flag) {
//		if (RanOutput == null)
    this.getpatten(row * col); // 이미지 랜덤으로 이미지를 뿌리고 그 값을
    //저장하고 그 후에 null이 아닐경우 생성하지 않는다

    this.row = row;
    this.col = col;
    this.btn_Item = this.row * this.col;

    this.coverBtns = new JButton[this.btn_Item];
    for (int index = 0; index < btn_Item; index++) {
      // 처음 아이콘 세팅
      this.coverBtns[index] = new JButton(new ImageIcon(RanOutput[index]));
      this.coverBtns[index].setEnabled(true);
    }

    jpnlCenter = new JPanel(new GridLayout(this.row, this.col, 10,
        10));
    for (JButton temp : this.coverBtns) {
      // System.out.println(index);
      jpnlCenter.add(temp);
    }
    this.add(jpnlCenter, BorderLayout.CENTER);
    jpnlCenter.setBackground(new Color(255, 217, 236));
  }

  //처음 text를 초기화 시켜주며 시간을 증가 시킨다
  private void setInit() {
    this.jlblRemain_Value.setText(String.valueOf(this.btn_Item / 2));
    this.jlblScore_Value.setText(String.valueOf(FindGame.scores));

    thread = new TimeThread(this, () -> initGameOverPane());
    thread.setDaemon(true);
    thread.start();
  }

  //시간의 값을 넣어주는 함수이다.
  public void setTime(int timer) {
    this.jlblTimer_Value.setText(String.valueOf(timer));
  }

  // 버튼에 액션 추가
  private void InsertAction() {
    for (int index = 0; index < this.btn_Item; index++) {
      coverBtns[index].addActionListener(this);
      coverBtns[index].setActionCommand(String.valueOf(index));
      coverBtns[index].setBackground(Color.pink);
    }
  }

  //main
  public static void main(String[] args) {
    new FindGame();
  }

	public void close() {
		clip.close();
	}
}
