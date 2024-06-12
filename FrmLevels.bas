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

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private LblSelect As Label
	Private BtnLvl1 As Button
	Private ImgLvl2Locked As ImageView
	Private ImgLvl3Locked As ImageView
	Private BtnLvl2 As Button
	Private BtnLvl3 As Button
	Private BtnBack As Button
	Private LblSelectShadow As Label
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	
	
	Activity.LoadLayout("FrmLevels")
	If Functions.Spanish = True Then
		LblSelect.Text = "Seleccionar Nivel"
		LblSelectShadow.Text = "Seleccionar Nivel"
		BtnLvl1.Text = "1 - Fácil"
		BtnLvl2.Text = "2 - Intermedio"
		BtnLvl3.Text = "3 - Experto"
	Else
		LblSelect.Text = "Level Select"
		LblSelectShadow.Text = "Level Select"
		BtnLvl1.Text = "1 - Easy"
		BtnLvl2.Text = "2 - Medium"
		BtnLvl3.Text = "3 - Hard"
	End If
End Sub

Sub Activity_Resume
	If Functions.Spanish = True Then
		LblSelect.Text = "Seleccionar Nivel"
		LblSelectShadow.Text = "Seleccionar Nivel"
		BtnLvl1.Text = "1 - Fácil"
		BtnLvl2.Text = "2 - Intermedio"
		BtnLvl3.Text = "3 - Experto"
	Else
		LblSelect.Text = "Level Select"
		LblSelectShadow.Text = "Level Select"
		BtnLvl1.Text = "1 - Easy"
		BtnLvl2.Text = "2 - Medium"
		BtnLvl3.Text = "3 - Hard"
	End If
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Private Sub BtnLvl1_Click
	If Functions.Spanish = True Then
		Functions.Levels = "Fácil"
	Else
		Functions.Levels = "Easy"
	End If
	StartActivity("FrmGamePlay")
End Sub

Private Sub BtnLvl2_Click
	If Functions.Spanish = True Then
		Functions.Levels = "Intermedio"
	Else
		Functions.Levels = "Medium"
	End If
	If Functions.Level2Pass = True Then
		StartActivity("FrmLevel2")
	Else
		MsgboxAsync("You need to win more levels to unlcok this one", "Level Locked")
	End If
	
End Sub

Private Sub BtnLvl3_Click
	If Functions.Spanish = True Then
		Functions.Levels = "Difícil"
	Else
		Functions.Levels = "Hard"
	End If
	If Functions.Level3Pass = True Then
		StartActivity("FrmLevel3")
	Else
		MsgboxAsync("You need to win more levels to unlcok this one", "Level Locked")
	End If
	
End Sub

Private Sub BtnBack_Click
	StartActivity("FrmTitleScreen")
End Sub
