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

	Private ImgBgNight As ImageView
	Private ImgGrassNight As ImageView
	Private ImgBot As ImageView
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
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("FrmLevel3")
	If Functions.Spanish = True Then
		LblScoreTxt.Text = "PUNTAJE"
		LblShot.Text = "TIRO"
	Else
		LblScoreTxt.Text = "SCORE"
		LblShot.Text = "SHOT"
	End If
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

End Sub

Private Sub ImgBot_Click
	
End Sub