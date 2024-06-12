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

	Private BtnBack As Button
	Private LblInfoText As Label
	Private LblAbout As Label
	Private LblAboutSHadow As Label
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("FrmAbout")
	If Functions.Spanish Then
		LblAbout.Text = "Acerca de"
		LblAboutSHadow.Text = "Acerca de"
		BtnBack.Text = "Atrás"
		LblInfoText.Text = "Diseñado y desarrollado por Luna López, Usé photoshop para los dibujos, botones y demás, y B4A para el desarrollo del juego, lo unico que no me pertenece es la música y los efectos de sonido usados, aquellos pertenecen a Nintendo, ah y la fuente tampoco es mía, la encontré gratis por ahí. Éste juego está basado en el clásico juego Duck Hunt(NES)"
	Else
		LblAbout.Text = "About"
		LblAboutSHadow.Text = "About"
		BtnBack.Text = "Back"
		LblInfoText.Text = "Designed and developed by me, yep, just me. I used photoshop for the drawings and stuff and B4A for developing the game, the only thing i don't own is the music and sound effects used, those belong to Nintendo. oh and the font isn't mine either, i found it free somewhere. This game is heavily inspired in the classic NES game Duck Hunt"
	End If
End Sub

Sub Activity_Resume
	If Functions.Spanish Then
		LblAbout.Text = "Acerca de"
		LblAboutSHadow.Text = "Acerca de"
		BtnBack.Text = "Atrás"
		LblInfoText.Text = "Diseñado y desarrollado por Luna López, Usé photoshop para los dibujos, botones y demás, y B4A para el desarrollo del juego, lo unico que no me pertenece es la música y los efectos de sonido usados, aquellos pertenecen a Nintendo, ah y la fuente tampoco es mía, la encontré gratis por ahí. Éste juego está basado en el clásico juego Duck Hunt(NES)"
	Else
		LblAbout.Text = "About"
		LblAboutSHadow.Text = "About"
		BtnBack.Text = "Back"
		LblInfoText.Text = "Designed and developed by me, yep, just me. I used photoshop for the drawings and stuff and B4A for developing the game, the only thing i don't own is the music and sound effects used, those belong to Nintendo. oh and the font isn't mine either, i found it free somewhere. This game is heavily inspired in the classic NES game Duck Hunt"
	End If
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Private Sub BtnBack_Click
	StartActivity("FrmOptions")
End Sub