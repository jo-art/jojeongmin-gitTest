# Puzzle_Game
Java 짝 맞추기 퍼즐 게임

- 짝 맞추기 퍼즐 
- 힌트 버튼 클릭 시 완성 퍼즐 5초간 보여줌  
- 한번 틀릴 때 마다 -5점, 맞추면 +20점  
- 힌트 사용 시 -20점  
-‘새로 시작’ 버튼을 누르면 퍼즐 크기 선택가능  
- 맞춘 횟수와 경과시간 ,점수를 보여줌  
- X 를 클릭하면 ‘게임을 끝내시겠습니까?’라고 물어봄

Class FindGame -> 실행 창 구성 , 게임 종료 시 팝업창  
Class GameBtns -> 랜덤으로 숫자를 뽑아 그 숫자에 해당하는 사진 버튼으로 나타냄   
Class imagehint -> 힌트클릭하면 이미지 보여줌    
Class imageThread -> 이미지 5초간 보여준 후 별 그림   
Class StartDlg  -> 팝업창으로 난이도 선택   
Class TimeThread -> 1~99초 까지 시간증가
