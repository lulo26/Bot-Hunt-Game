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
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private TxtUser As EditText
	Private TxtPass As EditText
	Private TxtName As EditText
	Private TxtEmail As EditText
	Private LblUser As Label
	Private LblPass As Label
	Private LblName As Label
	Private LblEmail As Label
	Private BtnExit As Button
	Private BtnRegister As Button
	Private LblCreateUser As Label
	Private LblCreateUserShadow As Label
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("FrmRegister")
	If Functions.Spanish = True Then
		LblUser.Text = "Usuario"
		LblPass.Text = "Contraseña"
		LblName.Text = "Nombre"
		LblEmail.Text = "Correo"
		BtnExit.Text = "Salir"
		BtnRegister.Text = "Registrarse"
		LblCreateUser.Text = "Crear Usuario"
		LblCreateUserShadow.Text = "Crear Usuario"
	Else
		LblUser.Text = "Username"
		LblPass.Text = "Password"
		LblName.Text = "Name"
		LblEmail.Text = "Email"
		BtnExit.Text = "Exit"
		BtnRegister.Text = "Register"
		LblCreateUser.Text = "Create User"
		LblCreateUserShadow.Text = "Create User"
	End If

End Sub

Sub Activity_Resume
	If Functions.Spanish = True Then
		LblUser.Text = "Usuario"
		LblPass.Text = "Contraseña"
		LblName.Text = "Nombre"
		LblEmail.Text = "Correo"
		BtnExit.Text = "Salir"
		BtnRegister.Text = "Registrarse"
		LblCreateUser.Text = "Crear Usuario"
		LblCreateUserShadow.Text = "Crear Usuario"
	Else
		LblUser.Text = "Username"
		LblPass.Text = "Password"
		LblName.Text = "Name"
		LblEmail.Text = "Email"
		BtnExit.Text = "Exit"
		BtnRegister.Text = "Register"
		LblCreateUser.Text = "Create User"
		LblCreateUserShadow.Text = "Create User"
	End If
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Private Sub BtnRegister_Click
	If TxtUser.Text.Length > 2 And TxtPass.Text.Length > 5 And TxtName.Text.Length > 2 And TxtEmail.Text.Length > 6 Then
		If File.Exists(File.DirInternal, "GameData.txt") Then
			'delete datos.txt file from internal files
			'File.Delete (File.DirInternal, "Data.txt")
			Dim ListData As List
			ListData = File.ReadList(File.DirInternal, "GameData.txt")
			If ListData.Size = 0 Then
				ListData.Add(TxtUser.Text & "," & TxtPass.Text & "," & TxtName.Text & "," & TxtEmail.Text & "," & "0")
			Else
				If Functions.Func_SearchUser(TxtUser.Text) Then
					MsgboxAsync ("user already exists", "Error!")
				Else
					ListData.Add(TxtUser.Text & "," & TxtPass.Text & "," & TxtName.Text & "," & TxtEmail.Text & "," & "0")
					File.WriteList(File.DirInternal, "GameData.txt", ListData)
					MsgboxAsync("User created successfully", "Congrats!")
					Wait For MsgBox_Result (Result As Int)
					If Result = DialogResponse.POSITIVE Then
						Activity.Finish
					End If
				End If
			End If
		Else
			File.Copy(File.DirAssets, "GameData.txt", File.DirInternal, "GameData.txt")
			Dim ListData As List
			ListData = File.ReadList(File.DirInternal, "GameData.txt")
			If ListData.Size = 0 Then
				ListData.Add(TxtUser.Text & "," & TxtPass.Text & "," & TxtName.Text & "," & TxtEmail.Text& "," & "0")
			Else
				File.WriteList(File.DirInternal,"GameData.txt", ListData)
				MsgboxAsync("User created successfully", "Congrats!")
				Wait For MsgBox_Result (Result As Int)
				If Result = DialogResponse.POSITIVE Then
					Activity.Finish
					
				End If
			End If
		End If
	Else
		MsgboxAsync("Make sure to fill all the required data", "Error!")
	End If
End Sub

Private Sub BtnExit_Click
	Activity.Finish
End Sub