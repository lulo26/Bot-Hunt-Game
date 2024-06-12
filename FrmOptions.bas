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

	Private LblOptions As Label
	Private BtnAbout As Button
	Private BtnLeadership As Button
	Private BtnLanguage As Button
	Private BtnBack As Button
	Private LblOptionsShadow As Label
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("FrmOptions")
	If Functions.Spanish = True Then
		LblOptions.Text = "Más Opciones"
		LblOptionsShadow.Text = "Más Opciones"
		BtnAbout.Text = "Acerca de"
		BtnLeadership.Text = "Puntajes"
		BtnLanguage.Text = "Idioma"
		BtnBack.Text = "Atrás"
	Else
		LblOptions.Text = "More Options"
		LblOptionsShadow.Text = "More Options"
		BtnAbout.Text = "About"
		BtnLeadership.Text = "Leadership"
		BtnLanguage.Text = "Language"
		BtnBack.Text = "Back"
	End If
End Sub

Sub Activity_Resume
	If Functions.Spanish = True Then
		LblOptions.Text = "Más Opciones"
		LblOptionsShadow.Text = "Más Opciones"
		BtnAbout.Text = "Acerca de"
		BtnLeadership.Text = "Puntajes"
		BtnLanguage.Text = "Idioma"
		BtnBack.Text = "Atrás"
	Else
		LblOptions.Text = "More Options"
		LblOptionsShadow.Text = "More Options"
		BtnAbout.Text = "About"
		BtnLeadership.Text = "Leadership"
		BtnLanguage.Text = "Language"
		BtnBack.Text = "Back"
	End If
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Private Sub BtnAbout_Click
	StartActivity("FrmAbout")
End Sub

Private Sub BtnLeadership_Click
	StartActivity("FrmLeadership")
End Sub

Private Sub BtnLanguage_Click
	StartActivity("Main")
End Sub

Private Sub BtnBack_Click
	StartActivity("FrmTitleScreen")
End Sub