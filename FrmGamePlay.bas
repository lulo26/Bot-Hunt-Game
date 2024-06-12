B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=12.8
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: true
	#IncludeTitle: False
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	
	'timers
	Dim TimerBot1 As Timer
	Dim TimerBotHit As Timer
	Dim TimerBotHitStop As Timer
	Dim TimerBotMiss As Timer
	Dim TimerBotMissDown As Timer
	Dim TimerBotMissRest As Timer
	Dim TimerBotMissStop As Timer
	Dim TimerBotMissDownStop As Timer
	Dim TimerBotMissRestStop As Timer
	'sounds
	Dim SoundShotWin As MediaPlayer
	Dim SoundMiss As MediaPlayer
	Dim SoundBotShot As MediaPlayer
	Dim SoundStageClear As MediaPlayer
	Dim soundLevelClear As MediaPlayer
	Dim SoundDefeat As MediaPlayer
	Dim SoundPerfectScore As MediaPlayer
	Dim SoundBotHurt As MediaPlayer
	Dim SoundBotFlying As MediaPlayer
	Dim SoundNoBullets As MediaPlayer
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Private ImgBgDay As ImageView
	Private ImgBot As ImageView
	Private ImgGrassDay As ImageView
	Private LblHit As Label
	Private ImgHit1 As ImageView
	Private ImgHit2 As ImageView
	Private ImgHit3 As ImageView
	Private ImgHit4 As ImageView
	Private ImgHit5 As ImageView
	Private ImgHit6 As ImageView
	Private LblScoreTxt As Label
	Private LblScore As Label
	Private LblShot As Label
	Private ImgShot1 As ImageView
	Private ImgShot2 As ImageView
	Private ImgShot3 As ImageView
	'game variants
	Dim clickShot As Int = 0
	Dim clickmiss As Int = 0
	Dim Score As Long
	Dim botRND As Int
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("FrmLevel1")
	FrmTitleScreen.SoundMedley.Pause
	'languages
	If Functions.Spanish = True Then
		LblScoreTxt.Text = "PUNTAJE"
		LblShot.Text = "TIRO"
	Else
		LblScoreTxt.Text = "SCORE"
		LblShot.Text = "SHOT"
	End If
	'game stuff
	Score = Functions.score
	'timers
	TimerBot1.Initialize ("TimerBot1", 50)
	TimerBot1.Enabled = True
	TimerBotHit.Initialize ("TimerBotHit", 100)
	TimerBotHitStop.Initialize ("TimerBotHitStop", 1000)
	TimerBotMiss.Initialize ("TimerBotMiss", 50)
	TimerBotMissDown.Initialize("TimerBotMissDown",50)
	TimerBotMissStop.Initialize ("TimerBotMissStop", 1600)
	TimerBotMissDownStop.Initialize("TimerBotMissDownStop",1600)
	TimerBotMissRest.Initialize("TimerBotMissRest",500)
	TimerBotMissRestStop.Initialize("TimerBotMissRestStop",1000)
	
	'sounds initialize
	SoundShotWin.Initialize
	SoundMiss.Initialize
	SoundBotShot.Initialize
	SoundStageClear.Initialize
	soundLevelClear.Initialize
	SoundDefeat.Initialize
	SoundPerfectScore.Initialize
	SoundBotFlying.Initialize
	SoundBotHurt.Initialize
	SoundNoBullets.Initialize
	'sounds define
	SoundShotWin.Load(File.DirAssets, "ShotWin.mp3")
	SoundMiss.Load(File.DirAssets, "Miss [laugh].mp3")
	SoundBotShot.Load(File.DirAssets, "Shot.mp3")
	SoundStageClear.Load(File.DirAssets, "Stage Clear.mp3")
	soundLevelClear.Load(File.DirAssets, "Level Clear.mp3")
	SoundDefeat.Load(File.DirAssets, "Defeat.mp3")
	SoundPerfectScore.Load(File.DirAssets, "Perfect Score.mp3")
	SoundBotHurt.Load(File.DirAssets, "BotHurt.mp3")
	SoundBotFlying.Load(File.DirAssets, "BotFlying.mp3")
	SoundNoBullets.Load(File.DirAssets, "NoMoreBullets.mp3")
	'bot position
	ImgBot.Left = 290dip
	ImgBot.Top = 340dip
End Sub

Sub Activity_Resume
	If Functions.Spanish = True Then
		LblScoreTxt.Text = "PUNTAJE"
		LblShot.Text = "TIRO"
	Else
		LblScoreTxt.Text = "SCORE"
		LblShot.Text = "SHOT"
	End If
End Sub

Sub Activity_Pause (UserClosed As Boolean)
	If FrmTitleScreen.soundoff = False Then
		FrmTitleScreen.SoundMedley.Play
		FrmTitleScreen.SoundMedley.Looping = True
	End If
	TimerBot1.Enabled = False
End Sub

'bot gets hit
Private Sub ImgBot_Click
	If clickmiss > 2 Then
		ImgBot.Enabled = False
	Else
		SoundBotShot.Play
	End If
	clickShot = clickShot + 1
	'score
	If clickShot = 1 And clickmiss < 3 Then
		ImgBot.Enabled = False
		ImgBot.Bitmap = LoadBitmap(File.DirAssets, "BotHit.png")
		ImgHit1.Bitmap = LoadBitmap(File.DirAssets, "HitsWin.png")
		SoundBotHurt.Play
		TimerBotHit.Enabled = True
		TimerBotHitStop.Enabled = True
		If clickmiss = 0 Then
			Score = Score + 1500
			LblScore.Text = Score
			clickmiss = 0
		End If
		If clickmiss = 1 Then
			Score = Score + 1000
			LblScore.Text = Score
			clickmiss = 0
		End If
		If clickmiss = 2 Then
			Score = Score + 500
			LblScore.Text = Score
			clickmiss = 0
		End If
		
	End If
End Sub

'miss click panels
Private Sub MissPanel_Click
	clickmiss = clickmiss + 1
	If clickmiss = 1 And clickShot < 1 Then 
		ImgShot1.Visible = False
		SoundBotShot.Play
	End If
	If clickmiss = 2 And clickShot < 1 Then
		ImgShot2.Visible = False
		SoundBotShot.Play
	End If
	If clickmiss = 3 And clickShot < 1 Then
		ImgHit1.Bitmap = LoadBitmap(File.DirAssets, "HitsMiss.png")
		ImgShot3.Visible = False
		SoundBotShot.Play
	End If
	If clickmiss > 3 Then
		SoundNoBullets.Play
	End If
End Sub
Private Sub MissPanel2_Click
	clickmiss = clickmiss + 1
	If clickmiss = 1 And clickShot < 1 Then
		ImgShot1.Visible = False
		SoundBotShot.Play
	End If
	If clickmiss = 2 And clickShot < 1 Then
		ImgShot2.Visible = False
		SoundBotShot.Play
	End If
	If clickmiss = 3 And clickShot < 1 Then
		ImgHit1.Bitmap = LoadBitmap(File.DirAssets, "HitsMiss.png")
		ImgShot3.Visible = False
		SoundBotShot.Play
	End If
	If clickmiss > 3 Then
		SoundNoBullets.Play
	End If
End Sub

'bot flying
Sub TimerBot1_Tick
	SoundBotFlying.Play
	ImgBot.Enabled = True
	ImgBot.Visible = True
	ImgBot.Top = ImgBot.Top - 5dip
	ImgBot.Left = ImgBot.Left + 5dip
	If clickShot = 1 And clickmiss < 3 Then
		TimerBot1.Enabled = False
		SoundBotFlying.Stop
	End If
	If ImgBot.Top = -170dip Or ImgBot.Left = 720dip Or ImgBot.Left = -150dip Then
		ImgBot.Visible = False
		ImgBot.Enabled = False
		ImgBot.Left = 290dip
		ImgBot.Top = 350dip
		TimerBot1.Enabled = False
		TimerBotMiss.Enabled = True
		TimerBotMissStop.Enabled = True
		ImgHit1.Bitmap = LoadBitmap(File.DirAssets, "HitsMiss.png")
	End If
End Sub

'miss animation
Sub TimerBotMiss_Tick
	ImgBot.Visible = True
	ImgBot.Enabled = False
	ImgBot.Bitmap = LoadBitmap(File.DirAssets, "gameplayBot.png")
	ImgBot.Top = ImgBot.Top - 7dip
End Sub
Sub TimerBotMissRest_tick
	ImgBot.Top = ImgBot.Top
End Sub
Sub TimerBotMissDown_Tick
	ImgBot.Top = ImgBot.Top + 8dip
End Sub
'miss animation stop
Sub TimerBotMissStop_Tick
	SoundMiss.Play
	TimerBotMiss.Enabled = False
	TimerBotMissStop.Enabled = False
	TimerBotMissRest.Enabled = True
	TimerBotMissRestStop.Enabled = True
End Sub
Sub TimerBotMissRestStop_tick
	TimerBotMissRest.Enabled = False
	TimerBotMissRestStop.Enabled = False
	TimerBotMissDown.Enabled = True
	TimerBotMissDownStop.Enabled = True
End Sub
Sub TimerBotMissDownStop_tick
	
	TimerBotMissDown.Enabled = False
	TimerBotMissDownStop.Enabled = False
End Sub

'hit animation
Sub TimerBotHit_Tick
	ImgBot.Visible = True
	botRND = Rnd(1,3)
	If botRND = 1 Then
		ImgBot.Bitmap = LoadBitmap(File.DirAssets, "BotHit2.png")
	End If
	If botRND = 2 Then
		ImgBot.Bitmap = LoadBitmap(File.DirAssets, "BotHit.png")
	End If
End Sub
'hit animation stop
Sub TimerBotHitStop_Tick
	TimerBotHit.Enabled = False
	TimerBotHitStop.Enabled = False
	ImgBot.Visible = False
End Sub

