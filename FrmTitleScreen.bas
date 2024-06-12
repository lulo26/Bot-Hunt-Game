B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=12.8
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: True
	#IncludeTitle: False
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	Dim SoundMedley As MediaPlayer
	dim soundoff as Boolean = False
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private BtnRegister As Button
	Private BtnLogIn As Button
	Private BtnOptions As Button
	Private BtnSound As ImageView
	Private BtnSoundOff As ImageView
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("FrmTitleScreen")
	If Functions.Spanish = True Then
		BtnRegister.Text = "Registrarse"
		BtnLogIn.Text = "Iniciar Sesión"
		BtnOptions.Text = "+ Opciones"
	Else
		BtnRegister.Text = "Register"
		BtnLogIn.Text = "Log In"
		BtnOptions.Text = "+ Options"
	End If
	
	'sounds
	SoundMedley.Initialize
	SoundMedley.Load(File.DirAssets, "Duck Hunt Medley.mp3")
	SoundMedley.Play
	SoundMedley.Looping = True

End Sub

Sub Activity_Resume
	If Functions.Spanish = True Then
		BtnRegister.Text = "Registrarse"
		BtnLogIn.Text = "Iniciar Sesión"
		BtnOptions.Text = "+ Opciones"
	Else
		BtnRegister.Text = "Register"
		BtnLogIn.Text = "Log In"
		BtnOptions.Text = "+ Options"
	End If
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Private Sub BtnRegister_Click
	StartActivity("FrmRegister")
End Sub

Private Sub BtnLogIn_Click
	StartActivity("FrmLogIn")
End Sub

Private Sub BtnOptions_Click
	StartActivity("FrmOptions")
End Sub

Private Sub BtnSound_Click
	soundoff = False
	SoundMedley.Play
	SoundMedley.Looping = True

		
End Sub

Private Sub BtnSoundOff_Click
	soundoff = True
	SoundMedley.Pause
End Sub