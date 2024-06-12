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

	Private LblUser As Label
	Private LblPass As Label
	Private TxtUser As EditText
	Private TxtPass As EditText
	Private BtnExit As Button
	Private BtnLogIn As Button
	Private LblLogIn As Label
	Private LblLogInShadow As Label
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("FrmLogIn")
	If Functions.Spanish = True Then
		LblUser.Text = "Usuario"
		LblPass.Text = "Contraseña"	
		BtnExit.Text = "Salir"
		BtnLogIn.Text = "Iniciar"
		LblLogIn.Text = "Iniciar Sesion"
		LblLogInShadow.Text = "Iniciar Sesion"
	Else
		LblUser.Text = "User"
		LblPass.Text = "Password"
		BtnExit.Text = "Exit"
		BtnLogIn.Text = "Start"
		LblLogIn.Text = "Log In"
		LblLogInShadow.Text = "Log In"
	End If
End Sub

Sub Activity_Resume
	If Functions.Spanish = True Then
		LblUser.Text = "Usuario"
		LblPass.Text = "Contraseña"
		BtnExit.Text = "Salir"
		BtnLogIn.Text = "Iniciar"
		LblLogIn.Text = "Iniciar Sesion"
		LblLogInShadow.Text = "Iniciar Sesion"
	Else
		LblUser.Text = "User"
		LblPass.Text = "Password"
		BtnExit.Text = "Exit"
		BtnLogIn.Text = "Start"
		LblLogIn.Text = "Log In"
		LblLogInShadow.Text = "Log In"
	End If
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Private Sub BtnLogIn_Click
	If File.Exists(File.DirInternal, "Data.txt") Then
		If Functions.Func_SearchUser(TxtUser.Text) Then
			If Functions.Func_ConfirmUser(TxtUser.Text, TxtPass.text) Then
				Functions.player = TxtUser.Text
				Functions.score=Functions.Func_SearchScore(TxtUser.Text)
				StartActivity("FrmLevels")
			Else
				MsgboxAsync("Please make sure the data is correct", "Wrong password!")
			End If
		Else
			MsgboxAsync("Please make sure the data is correct", "User not found!")
		End If
	Else
		MsgboxAsync("Please make sure to create a user before trying to log in", "Warning")
	End If
	
End Sub

Private Sub BtnExit_Click
	Activity.Finish
End Sub