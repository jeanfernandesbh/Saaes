package br.com.saaes.jsf.md;

import br.com.saaes.app.util.JsfUtil;
import br.com.saaes.app.util.TabViewMB;
import br.com.saaes.dao.DAO;
import br.com.saaes.facade.FacUtil;
import br.com.saaes.modelo.T200ies;
import br.com.saaes.modelo.T300cursos;
import br.com.saaes.modelo.T400docente;
import br.com.saaes.modelo.T400t300docentes;
import br.com.saaes.modelo.T500coordenador;
import br.com.saaes.modelo.T600bibliografia;
import br.com.saaes.modelo.T700avaliacao;
import br.com.saaes.modelo.T900Usuario;
import br.com.saaes.modelo.T901conceitos;
import br.com.saaes.modelo.T902titulacao;
import br.com.saaes.modelo.T903regimetrabalho;
import br.com.saaes.modelo.T904vinculoempregaticio;
import br.com.saaes.modelo.T905modalidade;
import br.com.saaes.modelo.T906tipoato;
import br.com.saaes.modelo.T907tipocurso;
import br.com.saaes.util.JPAUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author
 */
@ManagedBean(name = "cadastrar")
@ViewScoped
public class Cadastrar extends TabViewMB implements Serializable {

    EntityManager em;
    private T900Usuario usuario = (T900Usuario) FacUtil.getSession().getAttribute("usuario");

    private Integer abaAtiva = 0;
    private List<T300cursos> t300List;
    private List<T300cursos> t300ListSelds;
    private List<T200ies> t200list;
    private List<T400docente> t400DocenteList;
    private List<T400t300docentes> t400T300DocenteList;
    private List<T400docente> t400DocenteListSelds;
    private List<T500coordenador> t500CoordList;
    private List<T600bibliografia> t600BibliList;

    private List<T901conceitos> t901ConceitosList;
    private List<T902titulacao> t902TitulacaoList;
    private List<T903regimetrabalho> t903RegimeTrabalhoList;
    private List<T904vinculoempregaticio> t904VinculoEmpregaticioList;
    private List<T905modalidade> t905ModalidadeList;
    private List<T906tipoato> t906TipoAtoList;
    private List<T907tipocurso> t907TipoCursoList;

    private T200ies t200IesNova;
    private T200ies t200IesSeld;
    private T300cursos t300CursoSeld;
    private T300cursos t300NovoCurso;
    private T400docente t400NovoDocente;
    private T400t300docentes t400T300NovoDocente;
    private T500coordenador t500NovoCoord;
    private T600bibliografia t600NovaBibliografia;

    @PostConstruct
    public void init() {
        this.em = JPAUtil.getEm();
        t200IesSeld = new T200ies();
        t300CursoSeld = new T300cursos();
        t200IesNova = new T200ies();
        t400NovoDocente = new T400docente();
        t300NovoCurso = new T300cursos();
        t600NovaBibliografia = new T600bibliografia();

        t300List = new ArrayList<>();

        t400DocenteList = new ArrayList<>();
        t400T300DocenteList = new ArrayList<>();
        t600BibliList = new ArrayList<>();

        this.t300List = DAO.getFromNamedQuery(T300cursos.FIND_ALL, T300cursos.class, em);
        this.t200list = DAO.getFromNamedQuery(T200ies.FIND_ALL, T200ies.class, em);
        this.t400DocenteList = DAO.getFromNamedQuery(T400docente.FIND_ALL, T400docente.class, em);
        this.t500CoordList = DAO.getFromNamedQuery(T500coordenador.FIND_ALL, T500coordenador.class, em);
        this.t600BibliList = DAO.getFromNamedQuery(T600bibliografia.FIND_ALL, T600bibliografia.class, em);

        this.t901ConceitosList = DAO.getFromNamedQuery(T901conceitos.FIND_ALL, T901conceitos.class, em);
        this.t902TitulacaoList = DAO.getFromNamedQuery(T902titulacao.FIND_ALL, T902titulacao.class, em);
        this.t903RegimeTrabalhoList = DAO.getFromNamedQuery(T903regimetrabalho.FIND_ALL, T903regimetrabalho.class, em);
        this.t904VinculoEmpregaticioList = DAO.getFromNamedQuery(T904vinculoempregaticio.FIND_ALL, T904vinculoempregaticio.class, em);
        this.t905ModalidadeList = DAO.getFromNamedQuery(T905modalidade.FIND_ALL, T905modalidade.class, em);
        this.t906TipoAtoList = DAO.getFromNamedQuery(T906tipoato.FIND_ALL, T906tipoato.class, em);
        this.t907TipoCursoList = DAO.getFromNamedQuery(T907tipocurso.FIND_ALL, T907tipocurso.class, em);

    }

    /**
     * Seleciona IES
     */
    public void onSelectIes() {
        this.t300ListSelds = DAO.getFromNamedQuery(T300cursos.FIND_IES, T300cursos.class, em, t200IesSeld);
    }

    /**
     * Seleciona Docente
     */
    public void onSelectDocente() {
        this.t400DocenteListSelds = DAO.getFromNamedQuery(T400docente.FIND_IES, T400docente.class, em, t200IesSeld);
    }

    public void addT300Curso() {
        if (null != t300CursoSeld.getId()) {

            T700avaliacao t700 = DAO.getSingleResultFromFromNamedQuery(T700avaliacao.FIND_BY_T300, T700avaliacao.class, em, t300CursoSeld);
            if (null != t700) {
                 if (JOptionPane.showConfirmDialog(null,"Deseja inserir um docente em curso já avaliado?","Curso já foi Avaliado!",JOptionPane.YES_NO_OPTION)
                        == JOptionPane.YES_OPTION) {
                    excluirT700Avaliacao(t700);
                    t400T300NovoDocente = new T400t300docentes(t300CursoSeld);
                    this.t400T300DocenteList.add(t400T300NovoDocente);
                }
            } else {
                t400T300NovoDocente = new T400t300docentes(t300CursoSeld);
                this.t400T300DocenteList.add(t400T300NovoDocente);
            }

        }
    }

    /**
     * Cadastra nova instituição
     */
    public void insereNovaIes() {
        try {
            if (null != t200IesNova
                    && (!t200IesNova.getNome().equals("") && null != t200IesNova.getNome())
                    && (!t200IesNova.getCampus().equals("") && null != t200IesNova.getCampus())) {
                em.getTransaction().begin();

                t200IesNova.setT900UsuarioId(usuario);
                t200IesNova.setDtCadastro(JsfUtil.getInstante());
                DAO.save(t200IesNova, t200IesNova.getId(), em, Boolean.TRUE);

                em.getTransaction().commit();

                if (null == t200list) {
                    t200list = new ArrayList<>();
                }
                this.t200list.add(t200IesNova);
                this.t200IesNova = new T200ies();
                this.t200list = DAO.getFromNamedQuery(T200ies.FIND_ALL, T200ies.class, em);

                JsfUtil.addSuccessMessage("Instituição inserida com sucesso!");

            } else {
                JsfUtil.addAlertMessage("Informe o Nome e o Campus para cadastrar");
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Erro!", "Não foi possível inserir IES!");
            throw new IllegalStateException("Erro: " + e.getMessage());
        }
    }

    public void excluirT700Avaliacao(T700avaliacao t700) {
        try {
            if (null != t700) {
                em.getTransaction().begin();

                DAO.remove(t700, em, Boolean.TRUE);

                em.getTransaction().commit();

            }
        } catch (Throwable e) {
            throw new IllegalStateException("Erro: " + e.getMessage());
        }
    }

    public void excluirIes(T200ies t200) {
        try {
            if (null != t200) {
                em.getTransaction().begin();

                DAO.remove(t200, em, Boolean.TRUE);

                em.getTransaction().commit();

                this.t200list.remove(t200);

                JsfUtil.addSuccessMessage("Instituição removida com sucesso!");

            } else {
                JsfUtil.addAlertMessage("Falha ao excluir!");
            }
        } catch (Throwable e) {
            JsfUtil.addErrorMessage("Não foi possível excluir IES!", "Essa instituição faz parte de algum curso");
            throw new IllegalStateException("Erro: " + e.getMessage());
        }
    }

    /**
     * Insere novo curso
     */
    public void insereNovoCurso() {
        em = JPAUtil.getEm();
        try {
            if (null != t300NovoCurso
                    && (!t300NovoCurso.getNome().equals("") && null != t300NovoCurso.getNome())) {

                em.getTransaction().begin();
                // JsfUtil é uma classe que contem várias funcoes uteis
                t300NovoCurso.setDtCadastro(JsfUtil.getInstante());
                t300NovoCurso.setT900UsuarioId(usuario);
                DAO.save(t300NovoCurso, t300NovoCurso.getId(), em, Boolean.TRUE);
                em.getTransaction().commit();
                if (null == t300List) {
                    t300List = new ArrayList<>();
                }
                this.t300List.add(t300NovoCurso);

                t300NovoCurso = new T300cursos();
                t200IesSeld = new T200ies();
                t300CursoSeld = new T300cursos();
                JsfUtil.addSuccessMessage("Novo curso inserido com sucesso!");
            } else {
                JsfUtil.addAlertMessage("Informe o Nome e o Campus para cadastrar");
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Não foi possível inserir novo curso!");
            throw new IllegalArgumentException("Error " + e.getMessage());
        }
    }

    /**
     * Insere novo Docente
     */
    public void insereNovoDocente() {
        try {
            if (null != t400NovoDocente
                    && (!t400NovoDocente.getNome().equals("") && null != t400NovoDocente.getNome())) {

                em.getTransaction().begin();
                t400NovoDocente.setT900UsuarioId(usuario);
                // Seta a data de cadastro // JsfUtil é uma classe que contem várias funcoes uteis
                t400NovoDocente.setDtCadastro(JsfUtil.getInstante());
                t400NovoDocente.setT200IesId(t200IesSeld);

                DAO.save(t400NovoDocente, t400NovoDocente.getId(), em, Boolean.TRUE);

                for (T400t300docentes t400t300 : t400T300DocenteList) {
                    t400t300.setT400DocenteId(t400NovoDocente);
                    t400t300.setDtCadastro(JsfUtil.getInstante());
                    DAO.save(t400t300, t400t300.getId(), em, Boolean.TRUE);
                }
//                salvaT400T300Docente(Boolean.TRUE, em);

                em.getTransaction().commit();

                this.t400DocenteList = DAO.getFromNamedQuery(T400docente.FIND_ALL, T400docente.class, em);

                t400NovoDocente = new T400docente();
                t200IesSeld = new T200ies();
                t300CursoSeld = new T300cursos();
                JsfUtil.addSuccessMessage("Docente inserido com sucesso!");
            } else {
                JsfUtil.addAlertMessage("Informe um Nome para cadastrar");
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            JsfUtil.addErrorMessage("Não foi possível inserir novo docente!");
        }
    }

    public void salvaT400T300Docente(boolean transacional, EntityManager em) throws Exception {
        if (!transacional) {
            em.getTransaction().begin();
        }
        try {
            if (t400T300DocenteList.size() > 0) {
                for (T400t300docentes t400t300 : t400T300DocenteList) {
                    t400t300.setT400DocenteId(t400NovoDocente);
                    t400t300.setDtCadastro(JsfUtil.getInstante());
                    DAO.save(t400t300, t400t300.getId(), em, Boolean.TRUE);
                }
            }
        } catch (Exception ex) {
            em.getTransaction().setRollbackOnly();
            if (!transacional) {
                em.getTransaction().rollback();
            }
            Logger.getLogger(Cadastrar.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    /**
     * Insere novo Coordenador
     */
    public void insereNovoCoordenador() {
        try {
            if (null != t500NovoCoord) {
                em.getTransaction().begin();
                t500NovoCoord.setT900UsuarioId(usuario);
                t500NovoCoord.setDtCadastro(JsfUtil.getInstante());
                t500NovoCoord.setT300CursoId(t300CursoSeld);
                DAO.save(t500NovoCoord, t500NovoCoord.getId(), em, Boolean.TRUE);
                em.getTransaction().commit();

                if (null == t500CoordList) {
                    t500CoordList = new ArrayList<>();
                }
                this.t500CoordList.add(t500NovoCoord);
                t500NovoCoord = new T500coordenador();
                t200IesSeld = new T200ies();
                t300CursoSeld = new T300cursos();
                t400DocenteListSelds = new ArrayList<>();
                JsfUtil.addSuccessMessage("Coordenandor inserido com sucesso!");

            } else {
                JsfUtil.addAlertMessage("Informe um Nome para cadastrar");
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Não foi possível inserir novo Coordenador!");
        }
    }

    public void inserirBibliografia() {
        try {
            if (null != t600NovaBibliografia) {
                em.getTransaction().begin();
                t600NovaBibliografia.setT900UsuarioId(usuario);
                t600NovaBibliografia.setDtCadastro(JsfUtil.getInstante());
                t600NovaBibliografia.setT300CursoId(t300CursoSeld);

                DAO.save(t600NovaBibliografia, t600NovaBibliografia.getId(), em, Boolean.TRUE);
                em.getTransaction().commit();

                if (null == t600BibliList) {
                    t600BibliList = new ArrayList<>();
                }
                this.t600BibliList.add(t600NovaBibliografia);
                t600NovaBibliografia = new T600bibliografia();

                t300CursoSeld = new T300cursos();
                t200IesNova = new T200ies();
                JsfUtil.addSuccessMessage("Bibliografia inserida com sucesso!");

            } else {
                JsfUtil.addAlertMessage("Informe os dados para cadastrar");
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Não foi possível inserir nova Bibliografia!");
        }
    }

    public void tabChange(TabChangeEvent evt) {
        TabView tabView = (TabView) evt.getTab().getParent();
        abaAtiva = tabView.getActiveIndex();
        t200IesSeld = new T200ies();
        t300CursoSeld = new T300cursos();
        t200IesNova = new T200ies();
        t600NovaBibliografia = new T600bibliografia();
        t500NovoCoord = new T500coordenador();
        t400NovoDocente = new T400docente();
        t300NovoCurso = new T300cursos();
    }

    public T200ies getT200IesNova() {
        return t200IesNova;
    }

    public void setT200IesNova(T200ies t200IesNova) {
        this.t200IesNova = t200IesNova;
    }

    public List<T901conceitos> getT901ConceitosList() {
        return t901ConceitosList;
    }

    public void setT901ConceitosList(List<T901conceitos> T901ConceitosList) {
        this.t901ConceitosList = T901ConceitosList;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public List<T200ies> getT200list() {
        return t200list;
    }

    public void setT200list(List<T200ies> t200list) {
        this.t200list = t200list;
    }

    public Integer getAbaAtiva() {
        return abaAtiva;
    }

    public void setAbaAtiva(Integer AbaAtiva) {
        this.abaAtiva = AbaAtiva;
    }

    public List<T300cursos> getT300List() {
        return t300List;
    }

    public void setT300List(List<T300cursos> t300List) {
        this.t300List = t300List;
    }

    public T200ies getT200IesSeld() {
        return t200IesSeld;
    }

    public void setT200IesSeld(T200ies t200IesSeld) {
        this.t200IesSeld = t200IesSeld;
    }

    public T900Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(T900Usuario usuario) {
        this.usuario = usuario;
    }

    public List<T400docente> getT400DocenteList() {
        return t400DocenteList;
    }

    public void setT400DocenteList(List<T400docente> t400DocenteList) {
        this.t400DocenteList = t400DocenteList;
    }

    public List<T500coordenador> getT500CoordList() {
        return t500CoordList;
    }

    public void setT500CoordList(List<T500coordenador> t500CoordList) {
        this.t500CoordList = t500CoordList;
    }

    public T400docente getT400NovoDocente() {
        return t400NovoDocente;
    }

    public void setT400NovoDocente(T400docente t400NovoDocente) {
        this.t400NovoDocente = t400NovoDocente;
    }

    public T500coordenador getT500NovoCoord() {
        return t500NovoCoord;
    }

    public void setT500NovoCoord(T500coordenador t500NovoCoord) {
        this.t500NovoCoord = t500NovoCoord;
    }

    public T300cursos getT300NovoCurso() {
        return t300NovoCurso;
    }

    public void setT300NovoCurso(T300cursos t300NovoCurso) {
        this.t300NovoCurso = t300NovoCurso;
    }

    public T300cursos getT300CursoSeld() {
        return t300CursoSeld;
    }

    public void setT300CursoSeld(T300cursos t300CursoSeld) {
        this.t300CursoSeld = t300CursoSeld;
    }

    public List<T600bibliografia> getT600BibliList() {
        return t600BibliList;
    }

    public void setT600BibliList(List<T600bibliografia> t600BibliList) {
        this.t600BibliList = t600BibliList;
    }

    public T600bibliografia getT600NovaBibliografia() {
        return t600NovaBibliografia;
    }

    public void setT600NovaBibliografia(T600bibliografia t600NovaBibliografia) {
        this.t600NovaBibliografia = t600NovaBibliografia;
    }

    public List<T905modalidade> getT905ModalidadeList() {
        return t905ModalidadeList;
    }

    public void setT905ModalidadeList(List<T905modalidade> T905ModalidadeList) {
        this.t905ModalidadeList = T905ModalidadeList;
    }

    public List<T906tipoato> getT906TipoAtoList() {
        return t906TipoAtoList;
    }

    public void setT906TipoAtoList(List<T906tipoato> T906TipoAtoList) {
        this.t906TipoAtoList = T906TipoAtoList;
    }

    public List<T907tipocurso> getT907TipoCursoList() {
        return t907TipoCursoList;
    }

    public void setT907TipoCursoList(List<T907tipocurso> T907TipoCursoList) {
        this.t907TipoCursoList = T907TipoCursoList;
    }

    public List<T902titulacao> getT902TitulacaoList() {
        return t902TitulacaoList;
    }

    public void setT902TitulacaoList(List<T902titulacao> t902TitulacaoList) {
        this.t902TitulacaoList = t902TitulacaoList;
    }

    public List<T903regimetrabalho> getT903RegimeTrabalhoList() {
        return t903RegimeTrabalhoList;
    }

    public void setT903RegimeTrabalhoList(List<T903regimetrabalho> t903RegimeTrabalhoList) {
        this.t903RegimeTrabalhoList = t903RegimeTrabalhoList;
    }

    public List<T904vinculoempregaticio> getT904VinculoEmpregaticioList() {
        return t904VinculoEmpregaticioList;
    }

    public void setT904VinculoEmpregaticioList(List<T904vinculoempregaticio> t904VinculoEmpregaticioList) {
        this.t904VinculoEmpregaticioList = t904VinculoEmpregaticioList;
    }

    public List<T400docente> getT400DocenteListSelds() {
        return t400DocenteListSelds;
    }

    public void setT400DocenteListSelds(List<T400docente> t400DocenteListSelds) {
        this.t400DocenteListSelds = t400DocenteListSelds;
    }

    public List<T400t300docentes> getT400T300DocenteList() {
        return t400T300DocenteList;
    }

    public void setT400T300DocenteList(List<T400t300docentes> t400T300DocenteList) {
        this.t400T300DocenteList = t400T300DocenteList;
    }

    public T400t300docentes getT400T300NovoDocente() {
        return t400T300NovoDocente;
    }

    public void setT400T300NovoDocente(T400t300docentes t400T300NovoDocente) {
        this.t400T300NovoDocente = t400T300NovoDocente;
    }

    public List<T300cursos> getT300ListSelds() {
        return t300ListSelds;
    }

    public void setT300ListSelds(List<T300cursos> t300ListSelds) {
        this.t300ListSelds = t300ListSelds;
    }

}
