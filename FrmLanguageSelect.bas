B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=12.8
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: true
	#IncludeTitle: True
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private LblSelectLanguage As Label
	Private BtnSpanish As Button
	Private BtnEnglish As Button
	Private Label2 As Label
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("FrmLanguageSelect")
	If Functions.player = True Then
		LblSelectLanguage.Text = "Seleccione el Idioma"
		Label2.Text = "Seleccione el Idioma"
	Else
		
	End If
End Sub

Sub Activity_Resume
	If Functions.player = True Then
		LblSelectLanguage.Text = "Seleccione el Idioma"
		Label2.Text = "Seleccione el Idioma"
	End If
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Private Sub BtnSpanish_Click
	StartActivity("frmOptions")
	Functions.Spanish = True
End Sub

Private Sub BtnEnglish_Click
	StartActivity("FrmOptions")
	Functions.Spanish = False
End Sub